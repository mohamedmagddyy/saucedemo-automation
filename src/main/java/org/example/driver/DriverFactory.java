package org.example.driver;

import org.example.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DriverFactory class - Responsible for initializing and managing WebDriver instances
 * Thread-safe using ThreadLocal
 */
public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    public static void initializeDriver(String browserType) {

        if (driver.get() != null) {
            return; // already initialized
        }

        logger.info("Initializing {} browser", browserType);

        WebDriver localDriver;

        switch (browserType.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-save-password-bubble");
                chromeOptions.addArguments("--disable-features=PasswordLeakDetection");
                chromeOptions.setExperimentalOption("prefs", Map.of(
                    "credentials_enable_service", false,
                    "profile.password_manager_enabled", false,
                    "profile.password_manager_leak_detection", false
                ));

                // Add this line to disable the automation controlled flag
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

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
                logger.error("Browser not supported: {}", browserType);
                throw new RuntimeException("Browser not supported: " + browserType);
        }

        // Global settings
        localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.set(localDriver);
        logger.info("Driver initialized successfully");
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
            logger.info("Driver quit successfully");
        }
    }
}