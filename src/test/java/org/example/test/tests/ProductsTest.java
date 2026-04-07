package org.example.test.tests;

import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.example.test.base.BaseTest;
import org.example.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class ProductsTest extends BaseTest {

    private ProductsPage loginAndGoToProducts() {


        LoginPage loginPage = new LoginPage(driver);

        String username = ConfigReader.getProperty("validUsername", "standard_user");
        String password = ConfigReader.getProperty("validPassword", "secret_sauce");

        loginPage.login(username, password);

        ProductsPage productsPage = new ProductsPage(driver);

        // optional alert handling (clean)


        // validation
        Assert.assertEquals(productsPage.getPageTitle(), "Products",
                "[ASSERTION] User should be on Products page after login");

        return productsPage;
    }

//    @Test(description = "Verify Add to Cart button changes to Remove and persists in product details page")
//    public void testAddToCartButtonChangesAndPersists() {
//
//        ProductsPage productsPage = loginAndGoToProducts();
//
//        // dynamic product
//        Map<String, Double> products = productsPage.getAllProductsWithPrice();
//        String productName = products.keySet().iterator().next();
//
//        // add to cart
//        productsPage.addToCart(productName);
//
//        // ✅ clean validation
//        Assert.assertTrue(
//                productsPage.isRemoveButtonVisible(productName),
//                "[ASSERTION] Remove button should appear after adding product"
//        );
//
//        // go to details
//        productsPage.goToProductDetails(productName);
//
//        // verify title
//        Assert.assertEquals(
//                productsPage.getProductDetailsTitle(),
//                productName,
//                "[ASSERTION] Product title mismatch"
//        );
//
//        // verify remove still visible
//        Assert.assertTrue(
//                productsPage.isRemoveButtonVisible(productName),
//                "[ASSERTION] Remove button should persist in details page"
//        );
//    }

    // ================= Sorting Tests =================

    @Test(description = "Verify default sorting is A to Z")
    public void testDefaultSortingAToZ() {
        ProductsPage productsPage = loginAndGoToProducts();

        List<String> names = productsPage.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(String::compareToIgnoreCase);

        Assert.assertEquals(names, sorted,
                "[ASSERTION] Default sorting should be A to Z");
    }

    @Test(description = "Verify sorting Z to A")
    public void testSortingZToA() {
        ProductsPage productsPage = loginAndGoToProducts();

        productsPage.sortProducts("za");

        List<String> names = productsPage.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort((a, b) -> b.compareToIgnoreCase(a));

        Assert.assertEquals(names, sorted,
                "[ASSERTION] Sorting Z to A should work");
    }

    @Test(description = "Verify sorting Price Low to High")
    public void testSortingPriceLowToHigh() {
        ProductsPage productsPage = loginAndGoToProducts();

        productsPage.sortProducts("lohi");

        List<Double> prices = productsPage.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Double::compareTo);

        Assert.assertEquals(prices, sorted,
                "[ASSERTION] Prices should be sorted Low to High");
    }

    @Test(description = "Verify sorting Price High to Low")
    public void testSortingPriceHighToLow() {
        ProductsPage productsPage = loginAndGoToProducts();

        productsPage.sortProducts("hilo");

        List<Double> prices = productsPage.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort((a, b) -> b.compareTo(a));

        Assert.assertEquals(prices, sorted,
                "[ASSERTION] Prices should be sorted High to Low");
    }
}