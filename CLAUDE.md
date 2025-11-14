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

## Framework Architecture & Design Patterns

### Design Patterns Implemented

#### 1. Page Object Model (POM) Pattern
- **Location**: `src/main/java/pages/`
- **Implementation**: Each page (HomePage, LoginPage, CartPage, PaymentPage) encapsulates web elements and actions
- **Benefits**: Maintainable, reusable, reduces code duplication

#### 2. Interface Segregation
- **Locators**: Separated into `interfaces/` (PaymentPageLocators, URLs, etc.)
- **Clean separation**: UI locators decoupled from business logic
- **Constants management**: Centralized URL management in `URLs.java`

#### 3. Static Utility Pattern
- **BaseActions**: Static utility methods for common WebDriver operations
- **Advantages**: Reusable across all pages, centralized WebDriver management
- **Current scope**: 35+ utility methods covering clicks, waits, scrolling, alerts

#### 4. Builder/Factory Pattern (Partial)
- **BasePage**: Abstract base class providing common WebDriver functionality
- **Inheritance**: All pages extend BasePage for consistent structure

### Architectural Achievements

#### BDD Implementation
- **Cucumber Integration**: Gherkin feature files with TestNG runner
- **Step Definitions**: Modular step classes (aeLogin, aeCartPage, aeHome)
- **Test Organization**: Scenarios tagged with @smoke, @regression, @functional
- **Data-Driven**: Scenario Outlines with Examples tables

#### WebDriver Management
- **Singleton Pattern**: Static WebDriver instance in testBase.java
- **Lifecycle Management**: @Before/@After hooks for setup/teardown
- **Configuration**: Comprehensive ChromeOptions for CI/CD compatibility
- **Timeout Strategy**: Implicit, page load, and script timeouts configured

#### Wait Strategies
- **Multiple Approaches**: WebDriverWait, FluentWait, implicit waits
- **Smart Waiting**: Custom methods for visibility, clickability, URL changes
- **Polling**: FluentWait with 500ms polling intervals

### Current Framework Capabilities

#### Test Coverage
- **Login Flows**: Registration, authentication, logout, account deletion
- **E-commerce**: Product selection, cart management, checkout process
- **Payment Integration**: Full payment form handling and confirmation
- **Cross-browser**: Chrome with headless support

#### Reporting & Logging
- **Custom Logger**: Centralized logging in utils/Logger.java
- **Test Reports**: Cucumber HTML reports + TestNG integration
- **CI/CD Ready**: Jenkins pipeline with parameterized builds

## Improvement Roadmap

### Priority 1 (High Impact)

1. **Configuration Management**
   - Move hardcoded values to `application.properties`
   - Environment-specific configurations (dev/staging/prod)
   - Browser selection via properties

2. **Wait Strategy Consolidation**
   - Create dedicated `WaitUtils` class (note: file exists but BaseActions has TODO about moving waits)
   - Standardize timeout durations
   - Remove duplicate wait methods

3. **Exception Handling**
   - Custom exception classes for framework-specific errors
   - Try-catch blocks around critical operations
   - Meaningful error messages

### Priority 2 (Quality Improvements)

4. **Page Factory Pattern**
   - Implement `@FindBy` annotations
   - Reduce locator management overhead
   - Better element initialization

5. **Data Management**
   - External test data files (JSON/Excel)
   - Test data builders/factories
   - Environment-specific test data

6. **Parallel Execution**
   - ThreadLocal WebDriver instances
   - TestNG parallel configuration
   - Thread-safe utilities

### Priority 3 (Advanced Features)

7. **Reporting Enhancement**
   - Screenshot capture on failure
   - ExtentReports integration
   - Video recording capabilities

8. **API Testing Integration**
   - REST Assured framework
   - API validation alongside UI tests
   - Mock server capabilities
1
## Advanced Design Patterns Implementation

The framework supports implementing multiple advanced design patterns simultaneously. These patterns can be integrated with the existing POM structure for enhanced maintainability and portfolio demonstration.

### Implementable Design Patterns

#### 1. Single Responsibility Pattern
- **Implementation Area**: `src/main/java/pages/`
- **Structure**: Split current page classes into focused responsibilities
  - `pages/actions/` - User interaction methods (click, type, navigate)
  - `pages/validators/` - Assertions and validation logic
  - `pages/data/` - Page element management and data handling
- **Benefits**: Cleaner code separation, easier testing, improved maintainability

#### 2. Strategy Design Pattern
- **Implementation Area**: `src/main/java/strategies/`
- **Current Target**: Replace browser selection logic in `testBase.java`
- **Structure**:
  - `BrowserStrategy` interface
  - `ChromeStrategy`, `FirefoxStrategy`, `HeadlessStrategy` implementations
  - `BrowserContext` for strategy management
- **Integration**: Seamless replacement of existing browser configuration

#### 3. Factory Design Pattern
- **Implementation Area**: `src/main/java/factories/`
- **Components**:
  - `PageFactory` - Dynamic page object creation
  - `WebDriverFactory` - Browser instance management
  - `StrategyFactory` - Strategy pattern integration
- **Usage**: Replace direct instantiation throughout step definitions and test classes

#### 4. Execute Around Pattern
- **Implementation Area**: `src/main/java/executors/`
- **Components**:
  - `TestExecutor` - Wrap test operations with setup/teardown
  - `StepExecutor` - Pre/post step execution logic
  - Functional interfaces for flexible execution patterns
- **Integration**: Enhance existing TestNG lifecycle management

### Combined Implementation Strategy

All four patterns can be implemented simultaneously while maintaining:
- **Existing POM Structure**: Page objects remain the foundation
- **Cucumber Integration**: Step definitions enhanced with pattern usage
- **TestNG Compatibility**: Patterns integrate with current test lifecycle
- **CI/CD Pipeline**: No disruption to existing Jenkins configuration

### Integration Points
- **Step Definitions**: Utilize factories and executors for cleaner test code
- **testBase.java**: Integrate strategy pattern for browser management
- **Page Classes**: Apply single responsibility principle splitting
- **Test Runner**: Leverage execute around pattern for enhanced test lifecycle

This approach enables portfolio demonstration of multiple design patterns while maintaining framework functionality and adding architectural sophistication.