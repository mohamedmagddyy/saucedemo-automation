package org.example.test.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.example.test.base.BaseTest;
import org.example.pages.*;

public class CheckoutTest extends BaseTest {

    // FIXED - Class-level fields for page objects
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

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
        checkoutPage = new CheckoutPage(driver);
    }

    @Test(description = "Verify complete checkout flow")
    public void testCheckoutFlow() {

        SoftAssert soft = new SoftAssert();

        String productName = "Sauce Labs Backpack";

        // FIXED - Only test logic, no login needed
        productsPage.addToCart(productName);

        // badge check
        soft.assertEquals(cartPage.getCartBadgeCount(), 1);

        // open cart
        cartPage.clickCartIcon();

        // validate product
        soft.assertTrue(cartPage.isProductInCart(productName),
                "Product missing in cart!");

        cartPage.clickCheckout();

        // FIXED - Checkout flow with already initialized checkoutPage
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