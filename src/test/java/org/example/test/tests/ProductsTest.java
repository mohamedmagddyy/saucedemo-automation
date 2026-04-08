package org.example.test.tests;

import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.example.test.base.BaseTest;
import org.example.utils.AlertUtils;
import org.example.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

public class ProductsTest extends BaseTest {

    // FIXED - Class-level field for ProductsPage
    private ProductsPage productsPage;

    // FIXED - @BeforeMethod for login and page object initialization
    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        // FIXED - Login with valid credentials
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("validUsername", "standard_user");
        String password = ConfigReader.getProperty("validPassword", "secret_sauce");
        loginPage.login(username, password);
        // FIXED - Optional alert handling
        AlertUtils.acceptAlertIfPresent(driver);
        // FIXED - Initialize ProductsPage
        productsPage = new ProductsPage(driver);
        // FIXED - Validate we're on Products page
        Assert.assertEquals(productsPage.getPageTitle(), "Products",
                "[ASSERTION] User should be on Products page after login");
    }

    @Test(description = "Verify Add to Cart button changes to Remove and persists in product details page")
    public void testAddToCartButtonChangesAndPersists() {

        // FIXED - Only test logic, no login needed
        // dynamic product
        Map<String, Double> products = productsPage.getAllProductsWithPrice();
        String productName = products.keySet().iterator().next();

        // add to cart
        productsPage.addToCart(productName);

        // ✅ clean validation
        Assert.assertTrue(
                productsPage.isRemoveButtonVisible(productName),
                "[ASSERTION] Remove button should appear after adding product"
        );

        // go to details
        productsPage.goToProductDetails(productName);

        // verify title
        Assert.assertEquals(
                productsPage.getProductDetailsTitle(),
                productName,
                "[ASSERTION] Product title mismatch"
        );

        // verify remove still visible
        Assert.assertTrue(
                productsPage.isRemoveButtonVisibleOnDetailsPage(),
                "[ASSERTION] Remove button should persist in details page"
        );
    }

//     ================= Sorting Tests =================

    @Test(description = "Verify default sorting is A to Z")
    public void testDefaultSortingAToZ() {

        List<String> names = productsPage.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(String::compareToIgnoreCase);

        Assert.assertEquals(names, sorted,
                "[ASSERTION] Default sorting should be A to Z");
    }

    @Test(description = "Verify sorting Z to A")
    public void testSortingZToA() {

        productsPage.sortProducts("za");

        List<String> names = productsPage.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort((a, b) -> b.compareToIgnoreCase(a));

        Assert.assertEquals(names, sorted,
                "[ASSERTION] Sorting Z to A should work");
    }

    @Test(description = "Verify sorting Price Low to High")
    public void testSortingPriceLowToHigh() {

        productsPage.sortProducts("lohi");

        List<Double> prices = productsPage.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Double::compareTo);

        Assert.assertEquals(prices, sorted,
                "[ASSERTION] Prices should be sorted Low to High");
    }

    @Test(description = "Verify sorting Price High to Low")
    public void testSortingPriceHighToLow() {

        productsPage.sortProducts("hilo");

        List<Double> prices = productsPage.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort((a, b) -> b.compareTo(a));

        Assert.assertEquals(prices, sorted,
                "[ASSERTION] Prices should be sorted High to Low");
    }
}