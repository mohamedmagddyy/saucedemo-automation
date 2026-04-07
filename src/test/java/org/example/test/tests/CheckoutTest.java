package org.example.test.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.example.test.base.BaseTest;
import org.example.pages.*;

public class CheckoutTest extends BaseTest {

    @Test(description = "Verify complete checkout flow")
    public void testCheckoutFlow() {

        SoftAssert soft = new SoftAssert();

        String productName = "Sauce Labs Backpack";

        // ===== Login =====
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // ===== Products =====
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addToCart(productName); // ✅ الصح

        // ===== Cart =====
        CartPage cartPage = new CartPage(driver);

        // badge check
        soft.assertEquals(cartPage.getCartBadgeCount(), 1);

        // open cart
        cartPage.clickCartIcon();

        // validate product
        soft.assertTrue(cartPage.isProductInCart(productName),
                "Product missing in cart!");

        cartPage.clickCheckout();

        // ===== Checkout =====
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        checkoutPage.fillCheckoutInfo("Mohamed", "Magdy", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        // assertion
        soft.assertEquals(
                checkoutPage.getSuccessMessage(),
                "Thank you for your order!"
        );

        soft.assertAll();
    }
}