package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.example.base.BasePage;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutPage extends BasePage {

    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By FINISH_BUTTON = By.id("finish");
    private static final By SUCCESS_MESSAGE = By.cssSelector("[data-test='complete-header']");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    //checkout overview
    private static final By OVERVIEW_TITLE = By.cssSelector("[data-test='title']");
    private static final By CART_ITEMS = By.cssSelector("[data-test='inventory-item']");
    private static final By ITEM_PRICE = By.cssSelector("[data-test='inventory-item-price']");
    private static final By SUBTOTAL_LABEL = By.cssSelector("[data-test='subtotal-label']");
    private static final By TAX_LABEL = By.cssSelector("[data-test='tax-label']");
    private static final By TOTAL_LABEL = By.cssSelector("[data-test='total-label']");

    private static final By BACK_HOME_BUTTON = By.id("back-to-products");




    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

// ===== Form Actions =====

    public void fillCheckoutInfo(String firstName, String lastName, String zip) {
        type(FIRST_NAME, firstName);
        type(LAST_NAME, lastName);
        type(POSTAL_CODE, zip);
    }

    public void clickContinue() {
        click(CONTINUE_BUTTON);
    }

    public void clickFinish() {
        click(FINISH_BUTTON);
    }

    public void clickBackHome() {
        click(BACK_HOME_BUTTON);
    }

// ===== Page Titles & Messages =====

    public String getOverviewTitle() {
        return getText(OVERVIEW_TITLE);
    }

    public String getSuccessMessage() {
        return getText(SUCCESS_MESSAGE);
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }

// ===== Cart & Pricing =====

    public int getCartItemCount() {
        return driver.findElements(CART_ITEMS).size();
    }

    public double calculateTotalItemsPrice() {
        List<WebElement> prices = driver.findElements(ITEM_PRICE);
        double total = 0;
        for (WebElement price : prices) {
            total += Double.parseDouble(price.getText().replace("$", "").trim());
        }
        return total;
    }

    public double getSubtotalFromPage() {
        String text = getText(SUBTOTAL_LABEL);
        return Double.parseDouble(text.replace("Item total: $", "").trim());
    }

    public double getTaxFromPage() {
        String text = getText(TAX_LABEL);
        return Double.parseDouble(text.replace("Tax: $", "").trim());
    }

    public double getTotalFromPage() {
        String text = getText(TOTAL_LABEL);
        return Double.parseDouble(text.replace("Total: $", "").trim());
    }
}