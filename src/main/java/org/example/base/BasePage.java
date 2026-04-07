package org.example.base;

import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * BasePage class - Base class for all page objects
 * يحتوي على reusable actions لكل الصفحات
 */
public class BasePage {

    protected WebDriver driver;
    protected WaitUtils waitUtils;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void click(By by) {
        waitUtils.waitForElementToBeClickable(by);
        driver.findElement(by).click();
    }

    public void sendKeys(By by, String text) {
        waitUtils.waitForElementToBeVisible(by);
        WebElement element = driver.findElement(by);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By by) {
        waitUtils.waitForElementToBeVisible(by);
        return driver.findElement(by).getText();
    }

    public WebElement findElement(By by) {
        waitUtils.waitForElementToBeVisible(by);
        return driver.findElement(by);
    }

    public void clear(By by) {
        waitUtils.waitForElementToBeVisible(by);
        driver.findElement(by).clear();
    }

    public boolean isElementDisplayed(By by) {
        try {
            waitUtils.waitForElementToBeVisible(by);
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    protected void type(By locator, String text) {
        WebElement element = waitUtils.waitForElementToBeVisible(locator);
        element.clear();
        element.sendKeys(text);
    }
}