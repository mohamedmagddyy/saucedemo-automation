package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.example.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * CartPage class - Page Object for the Shopping Cart page
 */
public class CartPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(CartPage.class);

    // ===== Locators =====

    private static final By CART_TITLE = By.cssSelector("[data-test='title']");
    private static final By CART_ITEMS = By.cssSelector("[data-test='inventory-item']");
    private static final By ITEM_NAME = By.cssSelector("[data-test='inventory-item-name']");
    private static final By ITEM_PRICE = By.cssSelector("[data-test='inventory-item-price']");
    private static final By REMOVE_BUTTON = By.cssSelector("button[data-test^='remove']");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");

    private static final By first_name = By.id("firstName");
    private static final By last_name = By.id("lastName");
    private static final By postal_code = By.id("postal-code");
    private static final By continue_button = By.id("continue");
    private static final By CHECKOUT_TITLE = By.cssSelector("[data-test='title']");



    // cart icon + badge
    private static final By CART_ICON = By.id("shopping_cart_container");
    private static final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");

    // ===== Constructor =====
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ===== Actions & Validations =====

    public String getCartTitle() {
        return getText(CART_TITLE);
    }



    public String getCHECKOUTTitle() {
        return getText(CHECKOUT_TITLE);
    }

    public boolean isCartPageDisplayed() {
        return isElementDisplayed(CART_TITLE);
    }

    public boolean isCHECKOUTPageDisplayed() {
        return isElementDisplayed(CHECKOUT_TITLE);
    }

    public int getCartItemCount() {
        return driver.findElements(CART_ITEMS).size();
    }

    public String getItemNameByIndex(int index) {
        List<WebElement> items = driver.findElements(CART_ITEMS);
        return items.get(index).findElement(ITEM_NAME).getText();
    }

    public String getItemPriceByIndex(int index) {
        List<WebElement> items = driver.findElements(CART_ITEMS);
        return items.get(index).findElement(ITEM_PRICE).getText();
    }

    public boolean isProductInCart(String productName) {
        List<WebElement> items = driver.findElements(CART_ITEMS);
        for (WebElement item : items) {
            String name = item.findElement(ITEM_NAME).getText();
            if (name.equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    public String getProductPrice(String productName) {
        List<WebElement> items = driver.findElements(CART_ITEMS);
        for (WebElement item : items) {
            String name = item.findElement(ITEM_NAME).getText();
            if (name.equalsIgnoreCase(productName)) {
                return item.findElement(ITEM_PRICE).getText();
            }
        }
        return null;
    }

    public void removeItem(String productName) {
        logger.info("Removing item from cart: {}", productName);
        List<WebElement> items = driver.findElements(CART_ITEMS);
        for (WebElement item : items) {
            String name = item.findElement(ITEM_NAME).getText();
            if (name.equalsIgnoreCase(productName)) {
                item.findElement(REMOVE_BUTTON).click();
                break;
            }
        }
    }

    public void removeItem(int index) {
        List<WebElement> items = driver.findElements(CART_ITEMS);
        items.get(index).findElement(REMOVE_BUTTON).click();
    }

    public void clickCheckout() {
        click(CHECKOUT_BUTTON);
    }

    public void continueShopping() {
        click(CONTINUE_SHOPPING_BUTTON);
    }

    public void clickCartIcon() {
        click(CART_ICON);
    }

    public int getCartBadgeCount() {
        if (driver.findElements(CART_BADGE).isEmpty()) {
            return 0;
        }
        return Integer.parseInt(getText(CART_BADGE));
    }
}