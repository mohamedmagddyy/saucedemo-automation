package org.example.pages;

import org.example.base.BasePage;
import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.stream.Collectors;

public class ProductsPage extends BasePage {

    private WaitUtils waitUtils;

    // Page locators
    private static final By PRODUCTS_TITLE = By.cssSelector("span.title");
    private static final By PRODUCT_ITEMS = By.cssSelector(".inventory_item");
    private static final By PRODUCT_NAME = By.cssSelector(".inventory_item_name");
    private static final By PRODUCT_PRICE = By.cssSelector(".inventory_item_price");
    private static final By ADD_TO_CART_BUTTON = By.cssSelector(".btn_inventory");
    private static final By PRODUCT_TITLE_LINK = By.cssSelector("a[id$='_title_link']");
    private static final By SORT_DROPDOWN = By.cssSelector(".product_sort_container");
    private static final By REMOVE_BUTTON = By.cssSelector("button[data-test='remove']");
    private static final By PRODUCT_DETAILS_TITLE = By.cssSelector(".inventory_details_name");

    public ProductsPage(WebDriver driver) {
        super(driver);
        waitUtils = new WaitUtils(driver);
    }

    /** Page title */
    public String getPageTitle() {
        return waitUtils.waitForElementToBeVisible(PRODUCTS_TITLE).getText();
    }

    /** Read all products: name -> price */
    public Map<String, Double> getAllProductsWithPrice() {
        Map<String, Double> map = new LinkedHashMap<>();
        List<WebElement> items = waitUtils.waitForAllElementsToBeVisible(PRODUCT_ITEMS);
        for (WebElement item : items) {
            String name = item.findElement(PRODUCT_NAME).getText();
            String priceText = item.findElement(PRODUCT_PRICE).getText().replace("$", "");
            double price = Double.parseDouble(priceText);
            map.put(name, price);
        }
        return map;
    }

    /** Add product to cart */
    public void addToCart(String productName) {
        List<WebElement> items = waitUtils.waitForAllElementsToBeVisible(PRODUCT_ITEMS);
        for (WebElement item : items) {
            String name = item.findElement(PRODUCT_NAME).getText();
            if (name.equalsIgnoreCase(productName)) {
                item.findElement(ADD_TO_CART_BUTTON).click();
                break;
            }
        }
    }

    /** Check if Remove button is visible for a product */
    public boolean isRemoveButtonVisible(String productName) {
        List<WebElement> items = waitUtils.waitForAllElementsToBeVisible(PRODUCT_ITEMS);
        for (WebElement item : items) {
            String name = item.findElement(PRODUCT_NAME).getText();
            if (name.equalsIgnoreCase(productName)) {

                // ✅ wait جوه العنصر نفسه (مش global)
                WebElement removeBtn = waitUtils.waitForNestedElementToBeVisible(item, REMOVE_BUTTON);
                return removeBtn.isDisplayed();
            }
        }
        return false;
    }

    /** Go to product details page */
    public void goToProductDetails(String productName) {
        List<WebElement> items = waitUtils.waitForAllElementsToBeVisible(PRODUCT_ITEMS);
        for (WebElement item : items) {
            String name = item.findElement(PRODUCT_NAME).getText();
            if (name.equalsIgnoreCase(productName)) {
                item.findElement(PRODUCT_TITLE_LINK).click();
                break;
            }
        }
    }

    /** Get title on product details page */
    public String getProductDetailsTitle() {
        return waitUtils.waitForElementToBeVisible(PRODUCT_DETAILS_TITLE).getText();
    }

    /** Sort products */
    public void sortProducts(String value) {
        WebElement dropdown = waitUtils.waitForElementToBeClickable(SORT_DROPDOWN);
        dropdown.click();
        dropdown.findElement(By.cssSelector("option[value='" + value + "']")).click();
    }

    /** Get list of product names after sorting */
    public List<String> getProductNames() {
        return getAllProductsWithPrice().keySet().stream().collect(Collectors.toList());
    }

    /** Get list of product prices after sorting */
    public List<Double> getProductPrices() {
        return new ArrayList<>(getAllProductsWithPrice().values());
    }

}