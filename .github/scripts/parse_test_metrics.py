#!/usr/bin/env python3
"""
parse_test_metrics.py
---------------------
Parses Allure result JSON files and Surefire XML reports, then:
1. Pushes metrics to Grafana Cloud via Prometheus remote-write (snappy+protobuf)
   when GRAFANA_CLOUD_URL, GRAFANA_CLOUD_USER and GRAFANA_CLOUD_TOKEN are set.
2. Writes target/test-metrics.json consumed by the HTML dashboard generator.

Required env vars for Grafana Cloud push (optional — skip if not set):
  GRAFANA_CLOUD_URL   e.g. https://prometheus-prod-13-prod-us-east-0.grafana.net/api/prom/push
  GRAFANA_CLOUD_USER  numeric Grafana Cloud metrics user/instance ID
  GRAFANA_CLOUD_TOKEN Grafana Cloud API token with MetricsPublisher role
"""

import json
import os
import sys
import time
import glob
import struct
import xml.etree.ElementTree as ET
from datetime import datetime, timezone


# ── Allure results ────────────────────────────────────────────────────────────
def parse_allure_results(results_dir: str) -> dict:
    totals = {"passed": 0, "failed": 0, "broken": 0, "skipped": 0, "total": 0, "duration_ms": 0}
    if not os.path.isdir(results_dir):
        print(f"[WARN] Allure results dir not found: {results_dir}", file=sys.stderr)
        return totals
    for path in glob.glob(os.path.join(results_dir, "*-result.json")):
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


# ── Surefire XML results ──────────────────────────────────────────────────────
def parse_surefire_results(surefire_dir: str) -> dict:
    totals = {"passed": 0, "failed": 0, "broken": 0, "skipped": 0, "total": 0, "duration_ms": 0}
    if not os.path.isdir(surefire_dir):
        print(f"[WARN] Surefire reports dir not found: {surefire_dir}", file=sys.stderr)
        return totals
    for path in glob.glob(os.path.join(surefire_dir, "*.xml")):
        try:
            root = ET.parse(path).getroot()
            tests    = int(root.attrib.get("tests",    0))
            failures = int(root.attrib.get("failures", 0))
            errors   = int(root.attrib.get("errors",   0))
            skipped  = int(root.attrib.get("skipped",  0))
            time_s   = float(root.attrib.get("time",   0))
            totals["total"]       += tests
            totals["passed"]      += tests - failures - errors - skipped
            totals["failed"]      += failures
            totals["broken"]      += errors
            totals["skipped"]     += skipped
            totals["duration_ms"] += int(time_s * 1000)
        except Exception as e:
            print(f"[WARN] Could not parse {path}: {e}", file=sys.stderr)
    return totals


def merge(a: dict, b: dict) -> dict:
    return a if a["total"] >= b["total"] else b


# ── Hand-built Prometheus remote-write protobuf ───────────────────────────────
#
# Proto3 schema for Prometheus WriteRequest:
#
#   message WriteRequest { repeated TimeSeries timeseries = 1; }
#   message TimeSeries   { repeated Label  labels  = 1;
#                          repeated Sample samples = 2; }
#   message Label        { string name = 1; string value = 2; }
#   message Sample       { double value = 1; int64 timestamp = 2; }
#
# Wire encoding:
#   varint  = wire type 0
#   64-bit  = wire type 1  (doubles)
#   len-del = wire type 2  (strings, embedded messages)
#   tag     = (field_number << 3) | wire_type

def _varint(n: int) -> bytes:
    out = []
    while n > 0x7F:
        out.append((n & 0x7F) | 0x80)
        n >>= 7
    out.append(n)
    return bytes(out)


def _field_string(field_number: int, s: str) -> bytes:
    enc = s.encode("utf-8")
    return _varint((field_number << 3) | 2) + _varint(len(enc)) + enc


def _field_double(field_number: int, v: float) -> bytes:
    return _varint((field_number << 3) | 1) + struct.pack("<d", v)


def _field_int64(field_number: int, v: int) -> bytes:
    if v < 0:
        v += (1 << 64)
    return _varint((field_number << 3) | 0) + _varint(v)


def _field_embedded(field_number: int, payload: bytes) -> bytes:
    return _varint((field_number << 3) | 2) + _varint(len(payload)) + payload


def _encode_label(name: str, value: str) -> bytes:
    return _field_string(1, name) + _field_string(2, value)


def _encode_sample(value: float, timestamp_ms: int) -> bytes:
    return _field_double(1, value) + _field_int64(2, timestamp_ms)


def _encode_timeseries(labels: dict, metric_name: str, value: float, ts_ms: int) -> bytes:
    # __name__ label + user labels, sorted lexicographically (Prometheus requirement)
    all_labels = {"__name__": metric_name, **labels}
    label_bytes = b"".join(
        _field_embedded(1, _encode_label(k, v))
        for k, v in sorted(all_labels.items())
    )
    sample_bytes = _field_embedded(2, _encode_sample(value, ts_ms))
    return label_bytes + sample_bytes


def _encode_write_request(series_list: list) -> bytes:
    return b"".join(_field_embedded(1, ts) for ts in series_list)


# ── Grafana Cloud push ────────────────────────────────────────────────────────
def push_to_grafana_cloud(metrics: dict, run_id: str, branch: str, repo: str):
    url   = os.environ.get("GRAFANA_CLOUD_URL",   "").strip()
    user  = os.environ.get("GRAFANA_CLOUD_USER",  "").strip()
    token = os.environ.get("GRAFANA_CLOUD_TOKEN", "").strip()

    if not (url and user and token):
        print("[INFO] Grafana Cloud env vars not set — skipping remote-write push.")
        return

    try:
        import requests
    except ImportError:
        print("[WARN] 'requests' not installed — cannot push to Grafana Cloud.", file=sys.stderr)
        return

    try:
        import snappy
    except ImportError:
        print("[WARN] 'python-snappy' not installed — cannot push to Grafana Cloud.", file=sys.stderr)
        print("[INFO] Add 'pip install python-snappy' to the workflow pip install step.", file=sys.stderr)
        return

    ts_ms  = int(time.time() * 1000)
    labels = {"run_id": run_id, "branch": branch, "repository": repo}

    metric_values = {
        "selenium_tests_total":       float(metrics["total"]),
        "selenium_tests_passed":      float(metrics["passed"]),
        "selenium_tests_failed":      float(metrics["failed"]),
        "selenium_tests_broken":      float(metrics["broken"]),
        "selenium_tests_skipped":     float(metrics["skipped"]),
        "selenium_tests_duration_ms": float(metrics["duration_ms"]),
        "selenium_tests_pass_rate":   float(metrics["pass_rate"]),
    }

    series_list    = [_encode_timeseries(labels, name, val, ts_ms) for name, val in metric_values.items()]
    proto_bytes    = _encode_write_request(series_list)
    snappy_bytes   = snappy.compress(proto_bytes)   # raw snappy — what Prometheus remote-write expects

    headers = {
        "Content-Encoding":                   "snappy",
        "Content-Type":                        "application/x-protobuf",
        "X-Prometheus-Remote-Write-Version":   "0.1.0",
    }

    try:
        resp = requests.post(url, data=snappy_bytes, headers=headers, auth=(user, token), timeout=15)
        if resp.status_code in (200, 204):
            print(f"[INFO] Metrics pushed to Grafana Cloud ({resp.status_code}).")
        else:
            print(f"[WARN] Grafana Cloud push returned {resp.status_code}: {resp.text}", file=sys.stderr)
    except Exception as e:
        print(f"[WARN] Grafana Cloud push failed: {e}", file=sys.stderr)


# ── main ──────────────────────────────────────────────────────────────────────
def main():
    allure_dir   = os.environ.get("ALLURE_RESULTS_DIR",  "target/allure-results")
    surefire_dir = os.environ.get("SUREFIRE_REPORTS_DIR", "target/surefire-reports")
    output_file  = os.environ.get("METRICS_JSON_PATH",   "target/test-metrics.json")

    run_id     = os.environ.get("GITHUB_RUN_ID",     "local")
    branch     = os.environ.get("GITHUB_REF_NAME",   "unknown")
    repo       = os.environ.get("GITHUB_REPOSITORY", "unknown")
    run_number = os.environ.get("GITHUB_RUN_NUMBER", "0")
    actor      = os.environ.get("GITHUB_ACTOR",      "unknown")
    sha        = os.environ.get("GITHUB_SHA",        "unknown")[:7]

    allure   = parse_allure_results(allure_dir)
    surefire = parse_surefire_results(surefire_dir)
    metrics  = merge(allure, surefire)

    if metrics["total"] == 0:
        print("[WARN] No test results found. Metrics will be zeroed.", file=sys.stderr)

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

    os.makedirs(os.path.dirname(output_file) or ".", exist_ok=True)
    with open(output_file, "w") as f:
        json.dump(metrics, f, indent=2)
    print(f"[INFO] Metrics written to {output_file}")
    print(json.dumps(metrics, indent=2))

    push_to_grafana_cloud(metrics, run_id, branch, repo)


if __name__ == "__main__":
    main()