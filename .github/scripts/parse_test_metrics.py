#!/usr/bin/env python3
"""
parse_test_metrics.py
---------------------
Parses Allure result JSON files and Surefire XML reports, then:
  1. Pushes metrics to Grafana Cloud (Prometheus remote-write) when
     GRAFANA_CLOUD_URL, GRAFANA_CLOUD_USER and GRAFANA_CLOUD_TOKEN are set.
  2. Writes a metrics.json file consumed by the HTML dashboard generator.

Required env vars for Grafana Cloud push (optional — skip if not set):
  GRAFANA_CLOUD_URL   e.g. https://prometheus-prod-XX.grafana.net/api/prom/push
  GRAFANA_CLOUD_USER  numeric Grafana Cloud metrics user/instance ID
  GRAFANA_CLOUD_TOKEN Grafana Cloud API token with MetricsPublisher role
"""

import json
import os
import sys
import time
import glob
import xml.etree.ElementTree as ET
from datetime import datetime, timezone

# ── Allure results ───────────────────────────────────────────────────────────

def parse_allure_results(results_dir: str) -> dict:
    totals = {"passed": 0, "failed": 0, "broken": 0, "skipped": 0,
              "total": 0, "duration_ms": 0}
    if not os.path.isdir(results_dir):
        print(f"[WARN] Allure results dir not found: {results_dir}", file=sys.stderr)
        return totals
    pattern = os.path.join(results_dir, "*-result.json")
    files = glob.glob(pattern)
    for path in files:
        try:
            with open(path) as f:
                data = json.load(f)
            status = data.get("status", "unknown").lower()
            duration = data.get("time", {}).get("duration") or 0
            totals["duration_ms"] += duration
            totals["total"] += 1
            if status in totals:
                totals[status] += 1
        except Exception as e:
            print(f"[WARN] Could not parse {path}: {e}", file=sys.stderr)
    return totals


# ── Surefire XML results (fallback / supplement) ─────────────────────────────

def parse_surefire_results(surefire_dir: str) -> dict:
    totals = {"passed": 0, "failed": 0, "broken": 0, "skipped": 0,
              "total": 0, "duration_ms": 0}
    if not os.path.isdir(surefire_dir):
        print(f"[WARN] Surefire reports dir not found: {surefire_dir}", file=sys.stderr)
        return totals
    pattern = os.path.join(surefire_dir, "*.xml")
    files = glob.glob(pattern)
    for path in files:
        try:
            tree = ET.parse(path)
            root = tree.getroot()
            tests   = int(root.attrib.get("tests", 0))
            failures = int(root.attrib.get("failures", 0))
            errors   = int(root.attrib.get("errors", 0))
            skipped  = int(root.attrib.get("skipped", 0))
            time_s   = float(root.attrib.get("time", 0))
            passed   = tests - failures - errors - skipped
            totals["total"]       += tests
            totals["passed"]      += passed
            totals["failed"]      += failures
            totals["broken"]      += errors
            totals["skipped"]     += skipped
            totals["duration_ms"] += int(time_s * 1000)
        except Exception as e:
            print(f"[WARN] Could not parse {path}: {e}", file=sys.stderr)
    return totals


# ── Merge two result dicts, preferring non-zero values ───────────────────────

def merge(a: dict, b: dict) -> dict:
    if a["total"] >= b["total"]:
        return a
    return b


# ── Prometheus remote-write push ─────────────────────────────────────────────

def push_to_grafana_cloud(metrics: dict, run_id: str, branch: str, repo: str):
    url   = os.environ.get("GRAFANA_CLOUD_URL", "").strip()
    user  = os.environ.get("GRAFANA_CLOUD_USER", "").strip()
    token = os.environ.get("GRAFANA_CLOUD_TOKEN", "").strip()

    if not (url and user and token):
        print("[INFO] Grafana Cloud env vars not set — skipping remote-write push.")
        return

    # Lazy imports so the script still runs without these packages
    try:
        import requests
        import snappy
        from prometheus_client.exposition import _bton
        from prometheus_client.core import (
            CounterMetricFamily, GaugeMetricFamily
        )
        # Use the protobuf write path
        _push_protobuf(url, user, token, metrics, run_id, branch, repo)
    except ImportError:
        # Fallback: plain-text exposition (Grafana Cloud also accepts this
        # via the OpenMetrics / push-gateway compatible endpoint)
        _push_plaintext(url, user, token, metrics, run_id, branch, repo)


def _labels(run_id, branch, repo):
    return {"run_id": run_id, "branch": branch, "repository": repo}


def _push_plaintext(url, user, token, metrics, run_id, branch, repo):
    """Push metrics using Prometheus text exposition format."""
    try:
        import requests
    except ImportError:
        print("[WARN] 'requests' not installed — cannot push to Grafana Cloud.")
        return

    ts_ms = int(time.time() * 1000)
    label_str = ','.join(
        f'{k}="{v}"' for k, v in _labels(run_id, branch, repo).items()
    )

    lines = [
        f'# TYPE selenium_tests_total gauge',
        f'selenium_tests_total{{{label_str}}} {metrics["total"]} {ts_ms}',
        f'# TYPE selenium_tests_passed gauge',
        f'selenium_tests_passed{{{label_str}}} {metrics["passed"]} {ts_ms}',
        f'# TYPE selenium_tests_failed gauge',
        f'selenium_tests_failed{{{label_str}}} {metrics["failed"]} {ts_ms}',
        f'# TYPE selenium_tests_broken gauge',
        f'selenium_tests_broken{{{label_str}}} {metrics["broken"]} {ts_ms}',
        f'# TYPE selenium_tests_skipped gauge',
        f'selenium_tests_skipped{{{label_str}}} {metrics["skipped"]} {ts_ms}',
        f'# TYPE selenium_tests_duration_ms gauge',
        f'selenium_tests_duration_ms{{{label_str}}} {metrics["duration_ms"]} {ts_ms}',
        f'# TYPE selenium_tests_pass_rate gauge',
        f'selenium_tests_pass_rate{{{label_str}}} {metrics["pass_rate"]:.4f} {ts_ms}',
    ]
    body = "\n".join(lines) + "\n"

    resp = requests.post(
        url,
        data=body,
        # headers={"Content-Type": "text/plain"},
        headers={"Content-Type": "application/openmetrics-text; version=1.0.0; charset=utf-8"},
        auth=(user, token),
        timeout=15,
    )
    if resp.status_code in (200, 204):
        print(f"[INFO] Metrics pushed to Grafana Cloud ({resp.status_code}).")
    else:
        print(f"[WARN] Grafana Cloud push returned {resp.status_code}: {resp.text}",
              file=sys.stderr)


def _push_protobuf(url, user, token, metrics, run_id, branch, repo):
    """Push via Prometheus remote-write protobuf (preferred)."""
    try:
        import requests
        import snappy
        from prometheus_remote_write import write
        labels = _labels(run_id, branch, repo)
        ts_ms  = int(time.time() * 1000)
        series = [
            {"name": "selenium_tests_total",       "labels": labels, "value": metrics["total"],       "timestamp": ts_ms},
            {"name": "selenium_tests_passed",      "labels": labels, "value": metrics["passed"],      "timestamp": ts_ms},
            {"name": "selenium_tests_failed",      "labels": labels, "value": metrics["failed"],      "timestamp": ts_ms},
            {"name": "selenium_tests_broken",      "labels": labels, "value": metrics["broken"],      "timestamp": ts_ms},
            {"name": "selenium_tests_skipped",     "labels": labels, "value": metrics["skipped"],     "timestamp": ts_ms},
            {"name": "selenium_tests_duration_ms", "labels": labels, "value": metrics["duration_ms"],"timestamp": ts_ms},
            {"name": "selenium_tests_pass_rate",   "labels": labels, "value": metrics["pass_rate"],  "timestamp": ts_ms},
        ]
        write(url, series, auth=(user, token))
        print("[INFO] Metrics pushed via Prometheus remote-write protobuf.")
    except Exception as e:
        print(f"[WARN] Protobuf push failed ({e}), trying plaintext …")
        _push_plaintext(url, user, token, metrics, run_id, branch, repo)


# ── main ─────────────────────────────────────────────────────────────────────

def main():
    allure_dir   = os.environ.get("ALLURE_RESULTS_DIR",  "target/allure-results")
    surefire_dir = os.environ.get("SUREFIRE_REPORTS_DIR","target/surefire-reports")
    output_file  = os.environ.get("METRICS_JSON_PATH",   "target/test-metrics.json")
    run_id       = os.environ.get("GITHUB_RUN_ID",        "local")
    branch       = os.environ.get("GITHUB_REF_NAME",      "unknown")
    repo         = os.environ.get("GITHUB_REPOSITORY",    "unknown")
    run_number   = os.environ.get("GITHUB_RUN_NUMBER",    "0")
    actor        = os.environ.get("GITHUB_ACTOR",         "unknown")
    sha          = os.environ.get("GITHUB_SHA",            "unknown")[:7]

    allure   = parse_allure_results(allure_dir)
    surefire = parse_surefire_results(surefire_dir)
    metrics  = merge(allure, surefire)

    if metrics["total"] == 0:
        print("[WARN] No test results found in either Allure or Surefire directories. "
              "The build may have failed before tests ran. Metrics will be zeroed.",
              file=sys.stderr)

    total = metrics["total"]
    metrics["pass_rate"]    = (metrics["passed"] / total * 100) if total > 0 else 0
    metrics["fail_rate"]    = ((metrics["failed"] + metrics["broken"]) / total * 100) if total > 0 else 0
    metrics["duration_sec"] = metrics["duration_ms"] / 1000
    metrics["run_id"]       = run_id
    metrics["run_number"]   = run_number
    metrics["branch"]       = branch
    metrics["repository"]   = repo
    metrics["actor"]        = actor
    metrics["commit_sha"]   = sha
    metrics["timestamp"]    = datetime.now(timezone.utc).isoformat()

    # Write JSON for dashboard generator
    os.makedirs(os.path.dirname(output_file) or ".", exist_ok=True)
    with open(output_file, "w") as f:
        json.dump(metrics, f, indent=2)
    print(f"[INFO] Metrics written to {output_file}")
    print(json.dumps(metrics, indent=2))

    # Push to Grafana Cloud if configured
    push_to_grafana_cloud(metrics, run_id, branch, repo)


if __name__ == "__main__":
    main()

