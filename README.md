# SauceDemo Automation Framework

A clean, scalable, and professional Java Selenium Test Automation Framework using TestNG and Page Object Model (POM) design pattern.

## Project Structure

```
saucedemo automation/
├── src/
│   ├── main/java/org/example/
│   │   ├── base/
│   │   │   └── BasePage.java              # Base class for all page objects
│   │   ├── driver/
│   │   │   └── DriverFactory.java         # WebDriver factory and management
│   │   ├── pages/
│   │   │   ├── LoginPage.java             # Login page object
│   │   │   ├── ProductsPage.java          # Products/Inventory page object
│   │   │   └── CartPage.java              # Shopping cart page object
│   │   └── utils/
│   │       ├── ConfigReader.java          # Configuration properties reader
│   │       ├── WaitUtils.java             # Explicit wait utilities
│   │       ├── ActionsHelper.java         # Advanced Selenium actions
│   │       └── ScreenshotUtils.java       # Screenshot capture utilities
│   └── test/java/org/example/
│       ├── base/
│       │   └── BaseTest.java              # Base test class with setup/teardown
│       ├── tests/
│       │   ├── LoginTest.java             # Login test cases
│       │   └── CheckoutTest.java          # Checkout test cases
│       └── listeners/
│           └── TestListener.java          # TestNG listener for test events
├── src/test/resources/
│   ├── config.properties                  # Configuration properties
│   └── testng.xml                         # TestNG suite configuration
└── pom.xml                                 # Maven configuration

```

## Architecture Overview

### Design Pattern: Page Object Model (POM)

The framework follows the Page Object Model design pattern where:
- Each page in the application has a corresponding Java class
- Page elements (locators) are defined as static By objects
- Page actions are represented as methods
- Tests interact with pages rather than directly with WebDriver

### Key Components

#### 1. **DriverFactory** (Driver Management)
- Singleton pattern for WebDriver management
- Thread-safe using ThreadLocal
- Supports multiple browsers: Chrome, Firefox, Edge
- WebDriverManager integration for automatic driver binary management
- Methods:
  - `initializeDriver(String browserType)` - Initialize WebDriver
  - `getDriver()` - Get current WebDriver instance
  - `quitDriver()` - Clean up and quit driver

#### 2. **BasePage** (Page Object Base)
- Base class for all page objects
- Contains common interaction methods
- Initializes PageFactory for element locators
- Methods:
  - `click(By by)` - Click on element
  - `sendKeys(By by, String text)` - Send text to element
  - `getText(By by)` - Get text from element
  - `clear(By by)` - Clear text from element
  - `isElementDisplayed(By by)` - Check element visibility

#### 3. **Page Objects**

**LoginPage:**
- Locators for username field, password field, login button, error message
- Methods for entering credentials and retrieving messages

**ProductsPage:**
- Methods for adding products to cart
- Methods for opening cart
- Methods for product management

**CartPage:**
- Methods for checkout process
- Methods for removing items from cart
- Methods for cart management

#### 4. **Utilities**

**ConfigReader:**
- Reads configuration from config.properties
- Provides centralized property management
- Supports default values

**WaitUtils:**
- Explicit wait methods for stable element interactions
- Wait conditions: visibility, clickability, presence, invisibility
- Configurable wait timeouts

**ActionsHelper:**
- Advanced mouse and keyboard actions
- Hover, right-click, double-click operations
- Drag and drop functionality
- Scroll to element

**ScreenshotUtils:**
- Captures screenshots on test failure
- Auto-generates filenames with timestamps
- Organized screenshot storage

#### 5. **BaseTest** (Test Base)
- @BeforeMethod: setup() - Initialize WebDriver
- @AfterMethod: teardown() - Clean up resources
- Centralized test setup and teardown

#### 6. **TestListener** (Test Event Listener)
- Implements TestNG ITestListener interface
- Captures actions on test start, success, failure, skip
- Takes screenshots on test failure automatically
- Logs comprehensive test execution details

## Technology Stack

- **Language:** Java 17
- **Test Framework:** TestNG 7.8.1
- **Selenium:** WebDriver 4.15.0
- **Build Tool:** Maven 3.x
- **Driver Management:** WebDriverManager 5.6.3
- **Logging:** Log4j 2.21.0

## Configuration

### config.properties

Key configuration properties:
- `browser` - Browser type (chrome, firefox, edge)
- `base.url` - Application base URL
- `explicit.wait.timeout` - Explicit wait timeout in seconds
- `headless.mode` - Run browser in headless mode
- `screenshot.on.failure` - Enable/disable screenshot on failure
- Test credentials and data

## Test Execution

### Using Maven

Run all tests:
```bash
mvn clean test
```

Run specific test class:
```bash
mvn clean test -Dtest=LoginTest
```

Run specific test method:
```bash
mvn clean test -Dtest=LoginTest#testValidLogin
```

### Using TestNG XML

The `testng.xml` file in `src/test/resources/` controls test execution:
- Defines test suites and groups
- Registers test listeners
- Controls test execution order and parallelization

## Best Practices Implemented

1. **Separation of Concerns** - Clear separation between page objects, utilities, and tests
2. **DRY Principle** - Reusable base classes and utility methods
3. **Page Object Model** - Each page has a dedicated object class
4. **Thread Safety** - ThreadLocal for WebDriver management
5. **Configuration Management** - Externalized configuration via properties file
6. **Logging & Reporting** - Comprehensive test event logging and screenshot capture
7. **Waits** - Explicit waits instead of implicit waits for reliable test execution
8. **Clean Code** - Clear naming conventions and comprehensive documentation

## Future Enhancement Points

The skeleton includes placeholders (TODO comments) for implementation in the following areas:

1. **Page Locators** - Add By locators for page elements
2. **Method Implementations** - Implement page action methods
3. **Test Cases** - Complete test method implementations
4. **Configuration** - Configure driver options and browser preferences
5. **Logging** - Add detailed logging for debugging

## Getting Started

1. Clone/download the project
2. Update `config.properties` with your application URL and test data
3. Implement TODO comments in each class
4. Add page element locators (By objects)
5. Implement test methods
6. Run tests using Maven or TestNG

## Notes

- This is a framework skeleton - NO business logic is implemented
- All methods contain TODO comments and empty bodies for implementation
- The structure supports easy scaling for adding more test cases and pages
- Follow the established patterns when adding new pages or tests
- Ensure all locators are properly managed in their respective page classes

## Author

Senior QA Automation Engineer

## License

Internal Use Only

