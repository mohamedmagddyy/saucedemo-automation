package org.example.driver;

import org.example.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

/**
 * DriverFactory class - Responsible for initializing and managing WebDriver instances
 * Thread-safe using ThreadLocal
 */
public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver(String browserType) {

        if (driver.get() != null) {
            return; // already initialized
        }

        WebDriver localDriver;

        switch (browserType.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");

                localDriver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                localDriver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                localDriver = new EdgeDriver();
                break;

            default:
                throw new RuntimeException("Browser not supported: " + browserType);
        }

        // Global settings
        localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.set(localDriver);
    }

    public static WebDriver getDriver() {

        if (driver.get() == null) {
            throw new IllegalStateException("Driver is not initialized. Call initializeDriver() first.");
        }

        return driver.get();
    }

    public static void quitDriver() {

        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}