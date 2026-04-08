package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.example.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LoginPage class - Page Object for the Login page
 * Contains locators and methods specific to login functionality
 */
public class LoginPage extends BasePage {

    // Page locators
    private static final By USERNAME_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("h3[data-test='error']");

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    /**
     * Constructor - Initialize WebDriver for LoginPage
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enter username
     */
    public void enterUsername(String username) {
        clear(USERNAME_FIELD);
        sendKeys(USERNAME_FIELD, username);
    }

    /**
     * Enter password
     */
    public void enterPassword(String password) {
        clear(PASSWORD_FIELD);
        sendKeys(PASSWORD_FIELD, password);
    }

    /**
     * Click Login button
     */
    public void clickLogin() {
        click(LOGIN_BUTTON);
    }

    /**
     * Get error message text
     */
    public String getErrorMessage() {
        // بترجع نص الرسالة لو موجودة
        if (isElementDisplayed(ERROR_MESSAGE)) {
            String errorMessage = findElement(ERROR_MESSAGE).getText();
            logger.info("Error message found: {}", errorMessage);
            return errorMessage;
        } else {
            return ""; // لو مش موجودة
        }
    }

    /**
     * Perform login flow
     */
    public void login(String username, String password) {
        logger.info("Attempting login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}