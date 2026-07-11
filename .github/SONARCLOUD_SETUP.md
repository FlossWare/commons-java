# SonarCloud CI-Based Analysis Setup

## Issue

SonarCloud requires choosing **ONE** analysis method:
- **Automatic Analysis** (SonarCloud scans code automatically on push)
- **CI-Based Analysis** (GitHub Actions workflow runs the scan)

Running BOTH simultaneously causes the workflow to fail with errors like:
```
Automatic Analysis is enabled. Please disable it to use CI-based analysis.
```

## Solution

### Step 1: Disable Automatic Analysis (Manual - Required Once)

**IMPORTANT**: This cannot be done via API. You must use the SonarCloud web UI.

1. Navigate to the project analysis method settings:
   ```
   https://sonarcloud.io/project/administration/analysis_method?id=FlossWare_commons-java
   ```

2. Select **"CI-based analysis"**

3. Ensure **"Automatic Analysis"** is disabled/unchecked

### Step 2: Verify Workflow Configuration

The workflow in `.github/workflows/sonarcloud.yml` includes:

1. **Environment Variable**:
   ```yaml
   SONARCLOUD_SKIP_AUTOCONFIG: true
   ```
   Prevents the scanner from attempting to configure automatic analysis.

2. **Maven Parameter**:
   ```
   -Dsonar.scanner.skip.autoscan=true
   ```
   Explicitly disables autoscan behavior in the Maven plugin.

3. **Informational Check**:
   The workflow queries project settings and displays instructions if automatic analysis is still enabled.

## Troubleshooting

### Workflow Still Fails

If the workflow continues to fail after following Step 1:

1. **Clear SonarCloud cache**:
   - Go to: https://sonarcloud.io/project/administration?id=FlossWare_commons-java
   - Click "Delete project cache"

2. **Re-trigger the workflow**:
   ```bash
   git commit --allow-empty -m "Trigger SonarCloud scan"
   git push
   ```

### API Endpoints That DON'T Work

These API endpoints were attempted but are invalid/deprecated:
- `/api/navigation/set_analysis_mode` - Returns 404 "Unknown url"
- `/api/settings/set` with `key=sonar.autoscan` - May be restricted or invalid

**Conclusion**: Automatic Analysis can ONLY be disabled manually via the web UI.

## Valid API Endpoints

For reference, these API endpoints DO work:

- **Query project settings**:
  ```bash
  curl -u "$SONAR_TOKEN:" \
    "https://sonarcloud.io/api/settings/values?component=FlossWare_commons-java"
  ```

- **Get project details**:
  ```bash
  curl -u "$SONAR_TOKEN:" \
    "https://sonarcloud.io/api/projects/search?projects=FlossWare_commons-java"
  ```

## References

- [SonarCloud CI Integration Docs](https://docs.sonarcloud.io/advanced-setup/ci-based-analysis/github-actions-for-sonarcloud/)
- [Analysis Method Configuration](https://docs.sonarcloud.io/advanced-setup/automatic-analysis/)
