package org.example.test.base;

import org.example.driver.DriverFactory;
import org.example.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {

    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        logger.info("Setting up test - opening browser");
        DriverFactory.initializeDriver(ConfigReader.getProperty("browser", "chrome"));
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("baseUrl", "https://www.saucedemo.com"));
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down test - closing browser");
        DriverFactory.quitDriver();
    }

}