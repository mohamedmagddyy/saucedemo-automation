package org.example.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class WaitUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final long DEFAULT_WAIT_TIME = 10;
    private static final long POLLING_TIME = 500;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
    }

    // ===== Basic Waits =====

    public WebElement waitForElementToBeVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement waitForElementToBeClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public List<WebElement> waitForAllElementsToBeVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public boolean waitForElementToBeInvisible(By by) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    // ===== 🔥 Nested Element Wait (IMPORTANT) =====

    public WebElement waitForNestedElementToBeVisible(WebElement parent, By childLocator) {
        return wait.until(driver ->
                parent.findElement(childLocator).isDisplayed()
                        ? parent.findElement(childLocator)
                        : null
        );
    }

    public WebElement waitForNestedElementToBeClickable(WebElement parent, By childLocator) {
        return wait.until(driver -> {
            WebElement element = parent.findElement(childLocator);
            return (element.isDisplayed() && element.isEnabled()) ? element : null;
        });
    }

    // ===== 🔥 Fluent Wait (Retry Mechanism) =====

    public <T> T fluentWait(Function<WebDriver, T> condition, int timeoutSeconds) {

        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(POLLING_TIME))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return fluentWait.until(condition);
    }

    // ===== Custom Wait with Timeout =====

    public WebElement waitForElementToBeVisible(By by, long seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement waitForElementToBeClickable(By by, long seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(by));
    }
}