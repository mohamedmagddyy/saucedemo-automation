package org.example.test.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.example.driver.DriverFactory;
import org.example.utils.ScreenshotUtils;

import java.io.ByteArrayInputStream;

/**
 * TestListener class - Implements TestNG ITestListener for test execution events.
 * Captures screenshots on test failure, attaches them to Allure report,
 * and logs test execution details to the console.
 */
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("✅ Test Started: " + result.getMethod().getMethodName()
                + " | Class: " + result.getTestClass().getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✔ Test Passed: " + result.getMethod().getMethodName()
                + " | Duration: " + (result.getEndMillis() - result.getStartMillis()) + "ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        WebDriver driver = DriverFactory.getDriver();

        // --- Save screenshot to disk (existing behaviour) ---
        String screenshotPath = ScreenshotUtils.takeScreenshot(driver, testName);
        System.out.println("❌ Test Failed: " + testName);
        System.out.println("📸 Screenshot saved at: " + screenshotPath);

        // --- Attach screenshot to Allure report ---
        attachScreenshotToAllure(driver, testName);

        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            System.out.println("⚠ Exception: " + throwable.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⏭ Test Skipped: " + result.getMethod().getMethodName());
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            System.out.println("⚠ Skip reason: " + throwable.getMessage());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("⚠ Test failed but within success percentage: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("🏁 Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🏁 Test Suite Finished: " + context.getName());
        System.out.println("Summary: Passed=" + context.getPassedTests().size()
                + ", Failed=" + context.getFailedTests().size()
                + ", Skipped=" + context.getSkippedTests().size());
    }

    // -------------------------------------------------------------------------
    // Private helper — takes screenshot bytes from the driver and feeds them
    // directly to Allure so the report embeds the image inline.
    // -------------------------------------------------------------------------

    /**
     * Captures a PNG screenshot from the current driver session and attaches
     * it to the active Allure step/test as an inline image.
     *
     * @param driver   the active WebDriver instance
     * @param testName test method name — used as the attachment label
     */
    private void attachScreenshotToAllure(WebDriver driver, String testName) {
        try {
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(
                    "Screenshot on Failure — " + testName,
                    "image/png",
                    new ByteArrayInputStream(screenshotBytes),
                    "png"
            );
        } catch (Exception e) {
            System.out.println("⚠ Could not attach screenshot to Allure: " + e.getMessage());
        }
    }
}