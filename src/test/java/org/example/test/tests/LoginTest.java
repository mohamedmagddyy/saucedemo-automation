package org.example.test.tests;

import org.example.pages.ProductsPage;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.example.test.base.BaseTest;
import org.example.pages.LoginPage;
import org.example.utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test(description = "Login with valid credentials")
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
    public void testEmptyUsernameAndPassword() {
        LoginPage loginPage = new LoginPage(driver);

        // Login with empty username and password
        loginPage.login("", "");

        // Assert error message
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Epic sadface: Username is required");
    }

    @Test(description = "Login with empty username")
    public void testEmptyUsername() {
        LoginPage loginPage = new LoginPage(driver);

        // Login with empty username and valid password
        loginPage.login("", ConfigReader.getProperty("validPassword", "secret_sauce"));

        // Assert error message
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Epic sadface: Username is required");
    }

    @Test(description = "Login with empty password")
    public void testEmptyPassword() {
        LoginPage loginPage = new LoginPage(driver);

        // Login with valid username and empty password
        loginPage.login(ConfigReader.getProperty("validUsername", "standard_user"), "");

        // Assert error message
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Epic sadface: Password is required");
    }
}