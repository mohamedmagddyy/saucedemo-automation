package org.example.test.tests;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.example.test.base.BaseTest;
import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.example.pages.CartPage;

@Epic("SauceDemo E2E Tests")
@Feature("Shopping Cart")
public class CartTest extends BaseTest {

    // FIXED - Class-level fields for page objects
    private ProductsPage productsPage;
    private CartPage cartPage;

    // FIXED - @BeforeMethod for login and page object initialization
    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        // FIXED - Login with valid credentials
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        // FIXED - Initialize page objects
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test(description = "Verify adding product to cart and validating cart details")
    @Story("Add Product to Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Add 'Sauce Labs Backpack' to the cart, verify the badge count, open the cart, " +
                 "and assert that the product name and price are correct.")
    public void testAddProductToCart() {

        SoftAssert soft = new SoftAssert();

        String expectedName = "Sauce Labs Backpack";
        String expectedPrice = "$29.99";

        // FIXED - Only test logic, no login needed
        productsPage.addToCart(expectedName);

        // Validate badge
        soft.assertEquals(cartPage.getCartBadgeCount(), 1,
                "Badge count mismatch!");

        // Open cart
        cartPage.clickCartIcon();

        // Validate title
        soft.assertEquals(cartPage.getCartTitle(), "Your Cart");

        // Validate product exists
        soft.assertTrue(cartPage.isProductInCart(expectedName),
                "Product not found!");

        // Validate name
        soft.assertEquals(cartPage.getItemNameByIndex(0), expectedName);

        // Validate price
        soft.assertEquals(cartPage.getItemPriceByIndex(0), expectedPrice);

        soft.assertAll();
    }
}