#!/usr/bin/env python3
"""
generate_metrics_dashboard.py
------------------------------
Reads target/test-metrics.json (written by parse_test_metrics.py) and
produces a self-contained HTML file that looks like a Grafana dashboard.
No external dependencies — everything is inlined (Chart.js via CDN).

Usage (CI):
    python3 .github/scripts/generate_metrics_dashboard.py

Env vars (optional overrides):
    METRICS_JSON_PATH   default: target/test-metrics.json
    DASHBOARD_OUT_PATH  default: target/metrics-dashboard/index.html
"""

import json
import os
import sys
from datetime import datetime, timezone

METRICS_JSON = os.environ.get("METRICS_JSON_PATH",  "target/test-metrics.json")
DASHBOARD_OUT = os.environ.get("DASHBOARD_OUT_PATH", "target/metrics-dashboard/index.html")


def load_metrics() -> dict:
    if not os.path.exists(METRICS_JSON):
        print(f"[WARN] {METRICS_JSON} not found — using zeroed metrics.", file=sys.stderr)
        return {
            "passed": 0, "failed": 0, "broken": 0, "skipped": 0,
            "total": 0, "duration_ms": 0, "duration_sec": 0,
            "pass_rate": 0, "fail_rate": 0,
            "run_id": "N/A", "run_number": "N/A", "branch": "N/A",
            "repository": "N/A", "actor": "N/A", "commit_sha": "N/A",
            "timestamp": datetime.now(timezone.utc).isoformat(),
        }
    with open(METRICS_JSON) as f:
        return json.load(f)


def status_color(pass_rate: float) -> str:
    if pass_rate >= 90:
        return "#73BF69"   # Grafana green
    if pass_rate >= 70:
        return "#F2CC0C"   # Grafana yellow
    return "#F2495C"       # Grafana red


def build_html(m: dict) -> str:
    color       = status_color(m["pass_rate"])
    ts          = m.get("timestamp", "")[:19].replace("T", " ") + " UTC"
    duration_s  = f'{m.get("duration_sec", 0):.1f}s'
    pass_rate   = f'{m.get("pass_rate", 0):.1f}%'
    fail_rate   = f'{m.get("fail_rate", 0):.1f}%'

    passed  = m.get("passed",  0)
    failed  = m.get("failed",  0)
    broken  = m.get("broken",  0)
    skipped = m.get("skipped", 0)
    total   = m.get("total",   0)

    # Trend bar history (last run only — future runs append via GitHub Pages history)
    # We embed a minimal single-point trend; the dashboard self-describes.
    return f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1"/>
  <title>Test Metrics Dashboard — {m.get("repository","")}</title>
  <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.2/dist/chart.umd.min.js"></script>
  <style>
    *{{box-sizing:border-box;margin:0;padding:0}}
    body{{background:#111217;color:#d8d9da;font-family:'Helvetica Neue',Arial,sans-serif;padding:20px}}
    h1{{color:#e6e6e6;font-size:1.4rem;margin-bottom:4px}}
    .subtitle{{color:#6e9fff;font-size:.8rem;margin-bottom:20px}}
    .grid{{display:grid;grid-template-columns:repeat(auto-fit,minmax(160px,1fr));gap:12px;margin-bottom:20px}}
    .card{{background:#181b1f;border:1px solid #2c2f34;border-radius:6px;padding:16px}}
    .card .label{{font-size:.72rem;color:#8e8e8e;text-transform:uppercase;letter-spacing:.06em;margin-bottom:6px}}
    .card .value{{font-size:2rem;font-weight:700}}
    .card .sub{{font-size:.75rem;color:#8e8e8e;margin-top:4px}}
    .charts{{display:grid;grid-template-columns:1fr 1fr;gap:12px;margin-bottom:20px}}
    .chart-card{{background:#181b1f;border:1px solid #2c2f34;border-radius:6px;padding:16px}}
    .chart-card h3{{font-size:.8rem;color:#8e8e8e;text-transform:uppercase;letter-spacing:.06em;margin-bottom:12px}}
    canvas{{max-height:220px}}
    .meta{{background:#181b1f;border:1px solid #2c2f34;border-radius:6px;padding:14px;font-size:.8rem;line-height:1.8}}
    .meta span{{color:#6e9fff}}
    .badge{{display:inline-block;padding:2px 8px;border-radius:3px;font-size:.75rem;font-weight:600}}
    @media(max-width:600px){{.charts{{grid-template-columns:1fr}}}}
  </style>
</head>
<body>
  <h1>🧪 Test Metrics Dashboard</h1>
  <div class="subtitle">Run #{m.get("run_number","?")} &nbsp;·&nbsp; {m.get("branch","?")} &nbsp;·&nbsp; {ts}</div>

  <!-- Stat cards -->
  <div class="grid">
    <div class="card">
      <div class="label">Total Tests</div>
      <div class="value" style="color:#e6e6e6">{total}</div>
      <div class="sub">this run</div>
    </div>
    <div class="card">
      <div class="label">Passed</div>
      <div class="value" style="color:#73BF69">{passed}</div>
      <div class="sub">pass rate {pass_rate}</div>
    </div>
    <div class="card">
      <div class="label">Failed</div>
      <div class="value" style="color:#F2495C">{failed}</div>
      <div class="sub">assertion failures</div>
    </div>
    <div class="card">
      <div class="label">Broken</div>
      <div class="value" style="color:#FF9830">{broken}</div>
      <div class="sub">errors / exceptions</div>
    </div>
    <div class="card">
      <div class="label">Skipped</div>
      <div class="value" style="color:#F2CC0C">{skipped}</div>
      <div class="sub">not executed</div>
    </div>
    <div class="card">
      <div class="label">Duration</div>
      <div class="value" style="color:#6e9fff">{duration_s}</div>
      <div class="sub">total suite time</div>
    </div>
    <div class="card">
      <div class="label">Pass Rate</div>
      <div class="value" style="color:{color}">{pass_rate}</div>
      <div class="sub">target ≥ 90%</div>
    </div>
    <div class="card">
      <div class="label">Fail Rate</div>
      <div class="value" style="color:#F2495C">{fail_rate}</div>
      <div class="sub">failed + broken</div>
    </div>
  </div>

  <!-- Charts -->
  <div class="charts">
    <div class="chart-card">
      <h3>Result Distribution</h3>
      <canvas id="donutChart"></canvas>
    </div>
    <div class="chart-card">
      <h3>Pass vs Fail Breakdown</h3>
      <canvas id="barChart"></canvas>
    </div>
  </div>

  <!-- Run metadata -->
  <div class="meta">
    <div><span>Repository</span> &nbsp; {m.get("repository","N/A")}</div>
    <div><span>Branch</span> &nbsp; {m.get("branch","N/A")}</div>
    <div><span>Commit</span> &nbsp; {m.get("commit_sha","N/A")}</div>
    <div><span>Actor</span> &nbsp; {m.get("actor","N/A")}</div>
    <div><span>Run ID</span> &nbsp; {m.get("run_id","N/A")}</div>
    <div><span>Run #</span> &nbsp; {m.get("run_number","N/A")}</div>
    <div><span>Generated</span> &nbsp; {ts}</div>
  </div>

  <script>
    // Donut chart
    new Chart(document.getElementById('donutChart'), {{
      type: 'doughnut',
      data: {{
        labels: ['Passed','Failed','Broken','Skipped'],
        datasets: [{{
          data: [{passed},{failed},{broken},{skipped}],
          backgroundColor: ['#73BF69','#F2495C','#FF9830','#F2CC0C'],
          borderColor: '#111217',
          borderWidth: 3
        }}]
      }},
      options: {{
        plugins: {{
          legend: {{ labels: {{ color:'#d8d9da', font:{{ size:12 }} }} }}
        }},
        cutout: '65%'
      }}
    }});

    // Bar chart
    new Chart(document.getElementById('barChart'), {{
      type: 'bar',
      data: {{
        labels: ['Passed','Failed','Broken','Skipped'],
        datasets: [{{
          label: 'Tests',
          data: [{passed},{failed},{broken},{skipped}],
          backgroundColor: ['#73BF69','#F2495C','#FF9830','#F2CC0C'],
          borderRadius: 4
        }}]
      }},
      options: {{
        plugins: {{
          legend: {{ display: false }}
        }},
        scales: {{
          x: {{ ticks:{{ color:'#8e8e8e' }}, grid:{{ color:'#2c2f34' }} }},
          y: {{ ticks:{{ color:'#8e8e8e' }}, grid:{{ color:'#2c2f34' }}, beginAtZero:true }}
        }}
      }}
    }});
  </script>
</body>
</html>
"""


def main():
    m = load_metrics()
    html = build_html(m)
    out_dir = os.path.dirname(DASHBOARD_OUT)
    if out_dir:
        os.makedirs(out_dir, exist_ok=True)
    with open(DASHBOARD_OUT, "w") as f:
        f.write(html)
    print(f"[INFO] Dashboard written to {DASHBOARD_OUT}")


if __name__ == "__main__":
    main()

