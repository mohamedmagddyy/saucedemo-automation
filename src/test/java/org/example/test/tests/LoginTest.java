package org.example.test.tests;

import io.qameta.allure.*;
import org.example.pages.ProductsPage;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.example.test.base.BaseTest;
import org.example.pages.LoginPage;
import org.example.utils.ConfigReader;

@Epic("SauceDemo E2E Tests")
@Feature("Authentication")
public class LoginTest extends BaseTest {

    @Test(description = "Login with valid credentials")
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a standard user can log in with valid credentials and is redirected to the Products page.")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        // Login with valid credentials
        loginPage.login(
                ConfigReader.getProperty("validUsername", "standard_user"),
                ConfigReader.getProperty("validPassword", "secret_sauce")
        );

        // Assert that user navigated to inventory page
        String productsTitle = productsPage.getPageTitle();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "User should be on inventory page after login");
        Assert.assertEquals(productsTitle, "Products", "Page title should be 'Products' after login");
    }

    @Test(description = "Login with invalid credentials")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an error message is displayed when logging in with invalid credentials.")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);

        // Login with invalid credentials
        loginPage.login(
                ConfigReader.getProperty("invalidUsername", "locked_out_user"),
                ConfigReader.getProperty("invalidPassword", "wrong_password")
        );

        // Assert error message is displayed
        String errorMessage = loginPage.getErrorMessage();
        // FIXED
        Assert.assertFalse(errorMessage.isEmpty(), "Error message should not be empty");
    }

    @Test(description = "Login with both fields empty")
    @Story("Form Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that submitting the login form with both fields empty shows the correct error message.")
    public void testEmptyUsernameAndPassword() {
        LoginPage loginPage = new LoginPage(driver);

        // Login with empty username and password
        loginPage.login("", "");

        // Assert error message
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Epic sadface: Username is required");
    }

    @Test(description = "Login with empty username")
    @Story("Form Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that submitting the login form with an empty username shows the correct error message.")
    public void testEmptyUsername() {
        LoginPage loginPage = new LoginPage(driver);

        // Login with empty username and valid password
        loginPage.login("", ConfigReader.getProperty("validPassword", "secret_sauce"));

        // Assert error message
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Epic sadface: Username is required");
    }

    @Test(description = "Login with empty password")
    @Story("Form Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that submitting the login form with an empty password shows the correct error message.")
    public void testEmptyPassword() {
        LoginPage loginPage = new LoginPage(driver);

        // Login with valid username and empty password
        loginPage.login(ConfigReader.getProperty("validUsername", "standard_user"), "");

        // Assert error message
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Epic sadface: Password is required");
    }
}