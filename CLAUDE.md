# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Selenium-based test automation framework using Cucumber BDD with TestNG and Maven. The framework tests login functionality for a web application using Java 11.

## Technology Stack

- **Language**: Java 11
- **Build Tool**: Maven 3.9
- **Test Framework**: TestNG 7.11.0
- **BDD Framework**: Cucumber 7.28.2 with TestNG integration
- **Web Automation**: Selenium WebDriver 4.35.0
- **Browser Management**: WebDriverManager 5.9.2
- **Reporting**: ExtentReports 5.1.2 with Cucumber7 adapter
- **CI/CD**: Jenkins with parallel execution support

## Build and Test Commands

### Core Maven Commands
```bash
# Clean and compile
mvn clean compile

# Run all tests (uses testng.xml configuration)
mvn test

# Run tests with specific browser
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=chrome-headless

# Run tests with Cucumber tags
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@regression"
mvn test -Dcucumber.filter.tags="@Functional"

# Run single test class
mvn test -Dtest=TestRunner
```

### Browser Profiles
The project includes Maven profiles for different browsers:
- `chrome` (default)
- `firefox`
- `headless`

Use with: `mvn test -P headless`

## Project Structure

### Test Architecture
- **Test Runner**: `src/test/java/runners/TestRunner.java` - Cucumber TestNG integration point
- **Step Definitions**: `src/test/java/stepDefinitions/` - Cucumber step implementations
- **Base Test Class**: `src/test/java/stepDefinitions/testBase.java` - WebDriver setup and teardown
- **Feature Files**: `src/test/java/cucumber/features/` - Gherkin BDD scenarios
- **Test Configuration**: `testng.xml` - TestNG suite configuration

### Key Components
- WebDriver is managed as a static instance in `testBase.java` with automatic setup/teardown
- Uses WebDriverManager for automatic driver management
- Cucumber scenarios are tagged with `@smoke`, `@regression`, and `@Functional`
- Test reports are generated in `target/cucumber-reports/`

### CI/CD Integration
The project includes a comprehensive Jenkins pipeline (`JenkinsFile`) that:
- Supports parameterized builds (browser selection, test suite selection)
- Runs tests in parallel stages
- Generates HTML reports and JUnit XML results
- Sends metrics to InfluxDB for monitoring
- Archives test artifacts and screenshots

## Development Guidelines

### Adding New Tests
1. Create feature files in `src/test/java/cucumber/features/`
2. Implement step definitions in `src/test/java/stepDefinitions/`
3. Extend from `testBase.java` for WebDriver access
4. Use appropriate Cucumber tags for test categorization

### Browser Configuration
The framework supports browser configuration through system properties:
- Chrome (default)
- Firefox
- Chrome headless mode for CI/CD

Browser selection is handled in the base test class with ChromeOptions configured for CI environments.

### Test Execution Patterns
- Tests use TestNG's `@Before` and `@After` annotations for setup/teardown
- WebDriver instance is shared across step definitions via static reference
- Implicit wait is set to 10 seconds
- Tests are designed to run in Jenkins with both serial and parallel execution

## Reporting
- HTML reports: `target/cucumber-reports/`
- TestNG reports: `target/surefire-reports/`
- ExtentReports integration available
- Jenkins integration for report publishing