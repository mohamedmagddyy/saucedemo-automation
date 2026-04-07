package org.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

/**
 * ActionsHelper class - Utility for advanced Selenium Actions
 * Provides methods for mouse and keyboard interactions
 */
public class ActionsHelper {

    /**
     * Hover over an element by locator
     */
    public static void hoverOverElement(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        new Actions(driver).moveToElement(element).perform();
    }

    /**
     * Hover over a WebElement
     */
    public static void hoverOverElement(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    /**
     * Right click on an element by locator
     */
    public static void rightClick(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        new Actions(driver).contextClick(element).perform();
    }

    /**
     * Double click on an element by locator
     */
    public static void doubleClick(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        new Actions(driver).doubleClick(element).perform();
    }

    /**
     * Drag and drop one element to another
     */
    public static void dragAndDrop(WebDriver driver, By sourceLocator, By targetLocator) {
        WebElement source = driver.findElement(sourceLocator);
        WebElement target = driver.findElement(targetLocator);
        new Actions(driver).dragAndDrop(source, target).perform();
    }

    /**
     * Click and hold on an element
     */
    public static void clickAndHold(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        new Actions(driver).clickAndHold(element).perform();
    }

    /**
     * Release mouse button
     */
    public static void release(WebDriver driver) {
        new Actions(driver).release().perform();
    }

    /**
     * Scroll to a specific element
     */
    public static void scrollToElement(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}