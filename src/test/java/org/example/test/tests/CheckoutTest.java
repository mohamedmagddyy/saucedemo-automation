package org.example.test.tests;

import org.testng.Assert;
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

    @Test(description = "Verify error when all fields is empty")
    public void testEmptyallfields() {
        productsPage.addToCart("Sauce Labs Backpack");
        cartPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutPage.fillCheckoutInfo("", "", "");
        checkoutPage.clickContinue();

        Assert.assertEquals(
                checkoutPage.getErrorMessage(),
                "Error: First Name is required"
        );
    }



    @Test(description = "Verify error when First Name is empty")
    public void testEmptyFirstName() {
        productsPage.addToCart("Sauce Labs Backpack");
        cartPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutPage.fillCheckoutInfo("", "Magdy", "12345");
        checkoutPage.clickContinue();

        Assert.assertEquals(
                checkoutPage.getErrorMessage(),
                "Error: First Name is required"
        );
    }

    @Test(description = "Verify error when Last Name is empty")
    public void testEmptyLastName() {
        productsPage.addToCart("Sauce Labs Backpack");
        cartPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutPage.fillCheckoutInfo("Mohamed", "", "12345");
        checkoutPage.clickContinue();

        Assert.assertEquals(
                checkoutPage.getErrorMessage(),
                "Error: Last Name is required"
        );
    }

    @Test(description = "Verify error when Zip Code is empty")
    public void testEmptyZipCode() {
        productsPage.addToCart("Sauce Labs Backpack");
        cartPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutPage.fillCheckoutInfo("Mohamed", "Magdy", "");
        checkoutPage.clickContinue();

        Assert.assertEquals(
                checkoutPage.getErrorMessage(),
                "Error: Postal Code is required"
        );
    }

    @Test(description = "Verify overview page title, item count, and subtotal calculation")
    public void testCheckoutOverviewDetails() {

        SoftAssert soft = new SoftAssert();

        // Add 2 products
        productsPage.addToCart("Sauce Labs Backpack");   // $29.99
        productsPage.addToCart("Sauce Labs Bike Light"); // $9.99

        // Go to cart
        cartPage.clickCartIcon();
        cartPage.clickCheckout();

        // Fill info and continue to overview
        checkoutPage.fillCheckoutInfo("Mohamed", "Magdy", "12345");
        checkoutPage.clickContinue();

        // ✅ Assert 1: Overview title
        soft.assertEquals(
                checkoutPage.getOverviewTitle(),
                "Checkout: Overview",
                "[ASSERTION] Overview title mismatch"
        );

        // ✅ Assert 2: Item count
        int itemCount = checkoutPage.getCartItemCount();
        soft.assertEquals(
                itemCount,
                2,
                "[ASSERTION] Item count should be 2"
        );

        // ✅ Assert 3: Calculated totalItem == subtotal on page
        double calculatedTotal = checkoutPage.calculateTotalItemsPrice();
        double pageSubtotal = checkoutPage.getSubtotalFromPage();

        soft.assertEquals(
                calculatedTotal,
                pageSubtotal,
                "[ASSERTION] Calculated total should match page subtotal"
        );
        // ✅ Assert 4: Calculated totalItem + Tax == total

        double calculatedTax = pageSubtotal * 0.08; // Assuming 8% tax
        double expectedTotal = pageSubtotal + calculatedTax;
        double pageTotal = checkoutPage.getTotalFromPage();
        soft.assertEquals(
                pageTotal,
                expectedTotal,
                "[ASSERTION] Total on page should match calculated total + tax"
        );

        checkoutPage.clickFinish();

// ✅ Assert: Complete page title
        soft.assertEquals(
                checkoutPage.getOverviewTitle(),
                "Checkout: Complete!",
                "[ASSERTION] Complete page title mismatch"
        );

// ✅ Assert: Success message
        soft.assertEquals(
                checkoutPage.getSuccessMessage(),
                "Thank you for your order!",
                "[ASSERTION] Success message mismatch"
        );

        soft.assertAll();
    }

    @Test(description = "Verify cart badge is empty after completing order and going back home")
    public void testCartEmptyAfterCheckout() {

        // Add product and complete full checkout flow
        productsPage.addToCart("Sauce Labs Backpack");
        cartPage.clickCartIcon();
        cartPage.clickCheckout();
        checkoutPage.fillCheckoutInfo("Mohamed", "Magdy", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        // Back to home
        checkoutPage.clickBackHome();

        // ✅ Assert: Cart badge should be empty
        Assert.assertEquals(
                cartPage.getCartBadgeCount(),
                0,
                "[ASSERTION] Cart badge should be 0 after completing order"
        );
    }


}