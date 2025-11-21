# Test Execution Guide

This guide provides comprehensive instructions for running tests with different configurations using multiple Test Runners and Jenkins parameterized builds.

## Test Runners Overview

### Available Test Runners

1. **SmokeTestRunner** - Executes tests tagged with `@smoke`
2. **RegressionTestRunner** - Executes tests tagged with `@regression`
3. **FunctionalTestRunner** - Executes tests tagged with `@functional`
4. **AllTestRunner** - Executes all tests (no tag filtering)

### TestNG Configuration Files

Each test runner has corresponding TestNG XML configurations for different browser modes:

| Test Suite | Chrome Mode | Headless Mode |
|-----------|-------------|---------------|
| Smoke | `testng-smoke-chrome.xml` | `testng-smoke-headless.xml` |
| Regression | `testng-regression-chrome.xml` | `testng-regression-headless.xml` |
| Functional | `testng-functional-chrome.xml` | `testng-functional-headless.xml` |
| All Tests | `testng-all-chrome.xml` | `testng-all-headless.xml` |

## Local Test Execution

### Maven Commands

#### Using TestNG XML Files
```bash
# Smoke tests in Chrome
mvn test -DsuiteXmlFile=testng-smoke-chrome.xml

# Smoke tests in Headless mode
mvn test -DsuiteXmlFile=testng-smoke-headless.xml

# Regression tests in Chrome
mvn test -DsuiteXmlFile=testng-regression-chrome.xml

# Functional tests in Headless mode
mvn test -DsuiteXmlFile=testng-functional-headless.xml

# All tests in Chrome
mvn test -DsuiteXmlFile=testng-all-chrome.xml
```

#### Using System Properties
```bash
# Run specific tags with browser selection
mvn test -Dcucumber.filter.tags="@smoke" -Dbrowser=chrome
mvn test -Dcucumber.filter.tags="@regression" -Dbrowser=chrome-headless
mvn test -Dcucumber.filter.tags="@functional" -Dbrowser=firefox

# Run specific test runner classes
mvn test -Dtest=SmokeTestRunner -Dbrowser=chrome-headless
mvn test -Dtest=RegressionTestRunner -Dbrowser=chrome
```

#### Combining Multiple Tags
```bash
# Run smoke AND functional tests
mvn test -Dcucumber.filter.tags="@smoke and @functional"

# Run smoke OR functional tests
mvn test -Dcucumber.filter.tags="@smoke or @functional"

# Exclude specific tags
mvn test -Dcucumber.filter.tags="@functional and not @slow"
```

## Jenkins Configuration

### Setting Up Parameterized Build

1. **Create New Jenkins Job**
   - Job Type: Pipeline
   - Pipeline Definition: Pipeline script from SCM
   - Script Path: `Jenkinsfile-Parameterized`

2. **Build Parameters** (automatically configured from Jenkinsfile)
   - `TEST_SUITE`: Choose between smoke, regression, functional, all
   - `BROWSER_MODE`: Choose between chrome, headless
   - `ENVIRONMENT`: Choose between dev, staging, prod
   - `PARALLEL_EXECUTION`: Enable/disable parallel execution
   - `CUSTOM_TAGS`: Optional custom Cucumber tags

### Jenkins Build Trigger Examples

#### Scheduled Builds
```groovy
// Add to Jenkins pipeline triggers
triggers {
    // Run smoke tests every hour
    cron('H * * * *')  // For smoke suite

    // Run regression tests nightly
    cron('H 2 * * *')  // For regression suite

    // Run full suite weekly
    cron('H 2 * * 0')  // For all tests suite
}
```

#### Webhook Triggers
```groovy
// Trigger builds on code changes
triggers {
    githubPush()
    pollSCM('H/5 * * * *')
}
```

### Advanced Jenkins Configuration

#### Multi-Branch Pipeline
```groovy
// Jenkinsfile for feature branches
pipeline {
    agent any

    stages {
        stage('PR Validation') {
            when {
                changeRequest()
            }
            steps {
                script {
                    // Run smoke tests for PRs
                    sh "mvn test -DsuiteXmlFile=testng-smoke-headless.xml"
                }
            }
        }

        stage('Main Branch Tests') {
            when {
                branch 'main'
            }
            steps {
                script {
                    // Run full regression for main branch
                    sh "mvn test -DsuiteXmlFile=testng-regression-headless.xml"
                }
            }
        }
    }
}
```

#### Parallel Execution Pipeline
```groovy
pipeline {
    agent none

    stages {
        stage('Parallel Test Execution') {
            parallel {
                stage('Smoke Tests') {
                    agent any
                    steps {
                        sh "mvn test -DsuiteXmlFile=testng-smoke-headless.xml"
                    }
                }
                stage('Regression Tests') {
                    agent any
                    steps {
                        sh "mvn test -DsuiteXmlFile=testng-regression-headless.xml"
                    }
                }
            }
        }
    }
}
```

## Browser Configuration

### Supported Browsers
- **chrome**: Standard Chrome browser
- **chrome-headless**: Chrome in headless mode (for CI/CD)
- **firefox**: Firefox browser (if configured)

### Environment Variables
```bash
# Set browser via environment variable
export BROWSER=chrome-headless
mvn test -DsuiteXmlFile=testng-smoke-chrome.xml
```

## Reporting

### Report Locations
- **Cucumber HTML Reports**: `target/cucumber-reports/[suite]-tests.html`
- **Cucumber JSON Reports**: `target/cucumber-reports/[suite]-tests.json`
- **TestNG XML Reports**: `target/cucumber-reports/[suite]-tests.xml`
- **Surefire Reports**: `target/surefire-reports/`

### Jenkins Report Integration
- TestNG results published automatically
- HTML reports available in Jenkins job
- Screenshots archived on test failures

## Best Practices

### For Development
```bash
# Quick smoke test during development
mvn test -DsuiteXmlFile=testng-smoke-chrome.xml

# Test specific feature
mvn test -Dcucumber.filter.tags="@testing" -Dbrowser=chrome
```

## Troubleshooting

### Common Issues
1. **Browser Driver Issues**: Ensure WebDriverManager is properly configured
2. **Port Conflicts**: Use different ports for parallel execution
3. **Memory Issues**: Adjust JVM heap size for large test suites

### Debug Commands
```bash
# Run with debug logging
mvn test -DsuiteXmlFile=testng-smoke-chrome.xml -X

# Skip tests and just compile
mvn compile test-compile

# Clean and rebuild
mvn clean compile test -DsuiteXmlFile=testng-smoke-chrome.xml
```