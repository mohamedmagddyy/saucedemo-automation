# SauceDemo Test Automation Framework

A production-ready, enterprise-grade Java Selenium test automation framework built with TestNG, Page Object Model (POM) architecture, and comprehensive Allure reporting with detailed step tracking.

## Overview

This framework provides a solid foundation for automated testing of web applications using Selenium WebDriver 4 with a clean, scalable, and maintainable architecture. It includes professional reporting capabilities, cross-browser support, and comprehensive logging for debugging and test analysis.

## Project Structure

```
saucedemo-automation/
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── base/
│   │   │   │   └── BasePage.java              # Base class with reusable Selenium actions
│   │   │   ├── driver/
│   │   │   │   └── DriverFactory.java         # Thread-safe WebDriver management
│   │   │   ├── pages/
│   │   │   │   ├── LoginPage.java             # Login page object with @Step annotations
│   │   │   │   ├── ProductsPage.java          # Products/Inventory page object
│   │   │   │   ├── CartPage.java              # Shopping cart page object
│   │   │   │   └── CheckoutPage.java          # Checkout flow page object
│   │   │   └── utils/
│   │   │       ├── ConfigReader.java          # Externalized properties reader
│   │   │       ├── WaitUtils.java             # Explicit wait utilities
│   │   │       ├── ActionsHelper.java         # Advanced mouse/keyboard actions
│   │   │       ├── ScreenshotUtils.java       # Screenshot capture utilities
│   │   └── resources/
│   │       └── log4j2.xml                     # Log4j2 configuration
│   └── test/
│       ├── java/org/example/test/
│       │   ├── base/
│       │   │   └── BaseTest.java              # Base test class with @BeforeSuite/@AfterMethod
│       │   ├── tests/
│       │   │   ├── LoginTest.java             # Login test cases (5 test methods)
│       │   │   ├── ProductsTest.java          # Products page test cases
│       │   │   ├── CartTest.java              # Cart functionality test cases
│       │   │   └── CheckoutTest.java          # End-to-end checkout test cases (6 test methods)
│       │   └── listeners/
│       │       └── TestListener.java          # TestNG listener for test event hooks
│       └── resources/
│           ├── config.properties              # Test configuration (browser, base URL, credentials)
│           ├── allure.properties              # Allure configuration
│           └── testng.xml                     # TestNG suite configuration
├── logs/
│   └── test-execution.log                     # Test execution logs
├── screenshots/
│   └── [failure screenshots]                  # Screenshots captured on test failure
├── pom.xml                                    # Maven POM with all dependencies
└── README.md                                  # This file
```

## Current Features

### ✅ Page Object Model Architecture
- **Encapsulation**: Page elements and actions encapsulated in dedicated page classes
- **Maintainability**: Changes to page elements require updates in only one location
- **Reusability**: Common actions abstracted in BasePage class
- **Type Safety**: Uses Selenium's By locators with strong typing

### ✅ Allure Reporting Integration
- **Detailed Steps**: All page object methods annotated with `@Step` for detailed test timelines
- **Parameter Tracking**: Step parameters automatically captured and displayed
- **Environment Info**: Browser, OS, Java version, and application URL captured in report
- **Screenshots on Failure**: Automatically attached to failing test steps
- **Business-Level Reporting**: Steps written in user-facing business language

### ✅ WebDriver Management
- **Cross-Browser Support**: Chrome, Firefox, Edge (with WebDriverManager for automatic driver downloads)
- **Browser Options**: Pre-configured with stability enhancements (password manager disabled, etc.)
- **Window Management**: Automatic window maximize on browser launch

### ✅ Test Execution & Reporting
- **TestNG Framework**: Full TestNG support with listeners and event hooks
- **Test Organization**: Tests grouped by functionality (Login, Products, Cart, Checkout)
- **Parallel Execution**: Configurable via testng.xml
- **Screenshot Capture**: Automatic on test failure, stored with timestamps
- **Comprehensive Logging**: Log4j2 logging to console and file

### ✅ Utilities & Helpers
- **ConfigReader**: Centralized property management with default values
- **WaitUtils**: Explicit waits for reliability (visibility, clickability, presence)
- **ActionsHelper**: Advanced Selenium actions (hover, right-click, drag-and-drop)
- **ScreenshotUtils**: Screenshot capture and file management
- **AllureEnvironmentWriter**: Runtime environment data capture for Allure report

### ✅ Professional Code Quality
- **Separation of Concerns**: Clear boundaries between page objects, utilities, tests, and drivers
- **DRY Principle**: No code duplication; shared functionality in base classes
- **Clean Code**: Meaningful naming, single responsibility, minimal complexity
- **Error Handling**: Graceful exception handling with detailed logging
- **Documentation**: Comprehensive JavaDoc comments


## Test Suite

### Test Classes & Coverage

#### LoginTest (5 test methods)
- `testValidLogin()` - Valid credentials login flow
- `testInvalidLogin()` - Invalid credentials error validation
- `testEmptyUsernameAndPassword()` - Form validation with empty fields
- `testEmptyUsername()` - Field-level validation
- `testEmptyPassword()` - Field-level validation

#### ProductsTest (3+ test methods)
- `testAddToCartButtonChangesAndPersists()` - Add to cart button state tracking
- `testDefaultSortingAToZ()` - Default sort order validation
- `testSortingZToA()` - Reverse sort validation
- `testSortingPriceLowToHigh()` - Price ascending sort
- `testSortingPriceHighToLow()` - Price descending sort

#### CartTest (1+ test methods)
- `testAddProductToCart()` - Cart addition flow

#### CheckoutTest (6 test methods)
- `testCheckoutFlow()` - Complete end-to-end checkout
- `testEmptyallfields()` - Form validation with all empty fields
- `testEmptyFirstName()` - Individual field validation
- `testEmptyLastName()` - Individual field validation
- `testEmptyZipCode()` - Individual field validation
- `testCheckoutOverviewDetails()` - Order summary validation with price calculations
- `testCartEmptyAfterCheckout()` - Post-order state verification


