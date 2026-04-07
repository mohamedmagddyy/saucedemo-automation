package org.example.test.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.example.test.base.BaseTest;
import org.example.pages.LoginPage;
import org.example.pages.ProductsPage;
import org.example.pages.CartPage;

public class CartTest extends BaseTest {

    @Test(description = "Verify adding product to cart and validating cart details")
    public void testAddProductToCart() {

        SoftAssert soft = new SoftAssert();

        String expectedName = "Sauce Labs Backpack";
        String expectedPrice = "$29.99";

        // ===== Login =====
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // ===== Products =====
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addToCart(expectedName); // ✅ الصح

        // ===== Cart Page (نستخدمه للـ badge + click) =====
        CartPage cartPage = new CartPage(driver);

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