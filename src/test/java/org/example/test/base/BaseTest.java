package org.example.test.base;

import org.example.driver.DriverFactory;
import org.example.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverFactory.initializeDriver(ConfigReader.getProperty("browser", "chrome"));
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("baseUrl", "https://www.saucedemo.com"));
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

}