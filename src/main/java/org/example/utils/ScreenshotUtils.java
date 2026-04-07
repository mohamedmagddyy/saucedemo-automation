package org.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ScreenshotUtils class - Utility for taking screenshots during test execution
 * Saves screenshots with timestamp for better organization
 */
public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "screenshots/";

    /**
     * Take a screenshot and save it with provided test name
     *
     * @param driver   the WebDriver instance
     * @param testName the name of the test
     * @return the file path where screenshot is saved
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            createScreenshotDirIfNotExist();
            String filePath = SCREENSHOT_DIR + testName + "_" + getTimestamp() + ".png";
            Files.copy(srcFile.toPath(), Paths.get(filePath));
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Take a screenshot without explicit test name
     * Uses timestamp as default name
     *
     * @param driver the WebDriver instance
     * @return the file path where screenshot is saved
     */
    public static String takeScreenshot(WebDriver driver) {
        return takeScreenshot(driver, "screenshot");
    }

    /**
     * Get current timestamp for file naming
     *
     * @return formatted timestamp string
     */
    private static String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }

    /**
     * Create screenshots directory if not exists
     */
    private static void createScreenshotDirIfNotExist() throws IOException {
        File dir = new File(SCREENSHOT_DIR);
        if (!dir.exists()) {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
        }
    }
}