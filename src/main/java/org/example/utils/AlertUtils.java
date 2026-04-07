package org.example.utils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

/**
 * Utility class to handle browser alerts.
 */
public class AlertUtils {

    private WebDriver driver;

    public AlertUtils(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Accepts alert if present and prints its text.
     * Specifically handles "Change your password" alert.
     */
    public static void acceptAlertIfPresent(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            System.out.println("[ALERT TEXT] " + alert.getText());
            alert.accept();
            System.out.println("[ALERT ACCEPTED]");
        } catch (NoAlertPresentException e) {
            // No alert, do nothing
        }
    }
}

