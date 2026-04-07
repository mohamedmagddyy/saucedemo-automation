package org.example.test.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;
import org.openqa.selenium.WebDriver;
import org.example.driver.DriverFactory;
import org.example.utils.ScreenshotUtils;

/**
 * TestListener class - Implements TestNG ITestListener for test execution events
 * Captures screenshots on test failure and logs test execution details
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

        String screenshotPath = ScreenshotUtils.takeScreenshot(driver, testName);
        System.out.println("❌ Test Failed: " + testName);
        System.out.println("📸 Screenshot saved at: " + screenshotPath);

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
}