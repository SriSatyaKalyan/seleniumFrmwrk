import json, os, sys
import requests

url   = os.environ.get("GRAFANA_INSTANCE_URL", "").strip()
token = os.environ.get("GRAFANA_SERVICE_ACCOUNT_TOKEN", "").strip()

if not (url and token):
    print("[INFO] Grafana provisioning vars not set — skipping.")
    sys.exit(0)

with open("grafana-dashboard.json") as f:
    dashboard = json.load(f)

# Remove id/uid so Grafana treats it as a new/overwrite
dashboard.pop("id", None)

payload = {
    "dashboard": dashboard,
    "overwrite": True,
    "folderId": 0
}

resp = requests.post(
    f"{url}/api/dashboards/db",
    headers={"Content-Type": "application/json"},
    auth=("sa-1-github-actions-provisioner", token),  # Basic auth for Grafana Cloud
    json=payload,
    timeout=15
)

if resp.status_code == 200:
    print(f"[INFO] Dashboard provisioned successfully.")
    print(resp.json())
else:
    print(f"[WARN] Dashboard provisioning failed {resp.status_code}: {resp.text}", file=sys.stderr)
    sys.exit(1)