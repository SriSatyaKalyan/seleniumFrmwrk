# Grafana Metrics Setup

Every GitHub Actions run now produces a **Grafana-style HTML metrics dashboard**
that is published alongside the Allure report on GitHub Pages.

Optionally, you can stream live metrics into a real **Grafana Cloud** instance.

---

## 💰 Will Grafana Cloud cost anything?

**Short answer: No — this project will stay well within the free tier forever.**

Here's why. The Grafana Cloud **Free tier** (as of March 2026, no credit card required) includes:

| Resource | Free allowance | This project uses |
|---|---|---|
| Prometheus metrics | **10,000 active series/month** | **7 series per run** (one per metric) |
| Retention | 14 days | Enough for trend visibility |
| Dashboards & users | Unlimited on Free | ✅ |

We push exactly **7 metric series** per CI run (`total`, `passed`, `failed`, `broken`,
`skipped`, `pass_rate`, `duration_ms`). Even at 50 CI runs/day you'd be pushing
~350 series — a tiny fraction of the 10,000 free limit.

> ⚠️ The only way you'd ever pay is if you manually upgrade to Pro ($19/month) or
> exceed 10k active Prometheus series — which won't happen with test metrics alone.

---

## What runs automatically (no setup needed)

After every push/PR the workflow will:

1. Parse `target/allure-results` + `target/surefire-reports`.
2. Generate `target/test-metrics.json` with pass/fail/broken/skipped counts,
   pass rate, duration, branch, commit SHA, and run metadata.
3. Render a self-contained HTML dashboard (`target/metrics-dashboard/index.html`)
   with donut + bar charts (Chart.js, dark Grafana theme).
4. Publish the dashboard to GitHub Pages at:
   ```
   https://<org>.github.io/<repo>/metrics/
   ```
   (Allure report remains at the root `https://<org>.github.io/<repo>/`)

---

## Enabling live Grafana Cloud push (optional)

### 1 — Create a free Grafana Cloud account
Go to https://grafana.com/auth/sign-up/create-user (free tier is enough).

### 2 — Get your Prometheus remote-write credentials
1. In Grafana Cloud portal → **My Account** → your stack → **Details**.
2. Under **Prometheus** copy:
   - **Remote Write Endpoint** → this is your `GRAFANA_CLOUD_URL`
     e.g. `https://prometheus-prod-13-prod-us-east-0.grafana.net/api/prom/push`
   - **Username / Instance ID** → `GRAFANA_CLOUD_USER` (a number like `123456`)
3. Click **Generate now** (or **Manage API keys**) → create a token with the
   **MetricsPublisher** role → copy it → `GRAFANA_CLOUD_TOKEN`

### 3 — Add secrets to your GitHub repo
Go to **Settings → Secrets and variables → Actions → New repository secret**
and add:

| Secret name          | Value                              |
|----------------------|------------------------------------|
| `GRAFANA_CLOUD_URL`  | Remote Write Endpoint URL          |
| `GRAFANA_CLOUD_USER` | Instance ID (numeric)              |
| `GRAFANA_CLOUD_TOKEN`| API token (MetricsPublisher role)  |

### 4 — Import the dashboard in Grafana Cloud
1. In Grafana Cloud → **Dashboards → Import**.
2. Click **Upload JSON file** and select `grafana-dashboard.json` from the root of this repo.
3. Select your Prometheus data source when prompted.
4. The dashboard auto-populates **Repository** and **Branch** dropdowns from your metric labels.

The dashboard includes these panels:
- **6 stat cards** — Total, Passed, Failed, Broken, Skipped, Pass Rate gauge
- **Pass Rate Over Time** — trend line per branch
- **Test Counts Over Time** — stacked passed/failed/broken/skipped
- **Suite Duration** — how long the suite takes across runs
- **Failed + Broken Per Run** — stacked bar chart for failures

**Useful PromQL queries for panels:**

```promql
# Pass rate over time
selenium_tests_pass_rate{repository="your-org/your-repo"}

# Total tests per run
selenium_tests_total{repository="your-org/your-repo"}

# Failed + broken tests
selenium_tests_failed{repository="your-org/your-repo"}
+ selenium_tests_broken{repository="your-org/your-repo"}

# Suite duration in seconds
selenium_tests_duration_ms{repository="your-org/your-repo"} / 1000
```

---

## Metrics reference

| Metric name                   | Description                        |
|-------------------------------|------------------------------------|
| `selenium_tests_total`        | Total tests executed               |
| `selenium_tests_passed`       | Tests that passed                  |
| `selenium_tests_failed`       | Tests that failed (assertion)      |
| `selenium_tests_broken`       | Tests that errored (exception)     |
| `selenium_tests_skipped`      | Tests skipped                      |
| `selenium_tests_pass_rate`    | Pass rate 0–100                    |
| `selenium_tests_duration_ms`  | Total suite duration in ms         |

All metrics carry labels: `run_id`, `branch`, `repository`.

