package stepDefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.PaymentPage;
import pages.ProductPage;
import utils.BaseActions;

import java.util.HashMap;

public class aeCartPage {

    // TODO: Needs to be modified. No sout's
    public ProductPage productPage = new ProductPage(getDriver());
    public CartPage cart = new CartPage(getDriver());
    public PaymentPage paymentPage = new PaymentPage(getDriver());

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @And("User clicks on Add To Cart {string} times")
    public void userClicksOnAddToCartNumberTimes(String number) {
        productPage.addNumberProductsToCart(number);
    }

    @Then("User observes Cart contains {string} {string} times")
    public void userObservesCartContainsProductNumberTimes(String product, String number) throws InterruptedException {
        productPage.verifyNumberProductsInCart(product, number);
    }

    @When("User adds {string} to cart")
    public void userAddsProductsToCart(String products) {
        productPage.addProductstoCart(products);
    }

    @Then("User observes Cart contains {string}")
    public void userObservesCartContainsProducts(String products) {
        cart.userVerifiesCartContainingProducts(products);
    }

    @When("User removes {string} from Cart")
    public void userRemovesToBeRemovedProductFromCart(String toBeRemovedProduct) {
        cart.userRemovesProduct(toBeRemovedProduct);
    }

    @When("User clicks on Proceed To Checkout")
    public void userClicksOnProceedToCheckout() {
        productPage.userClicksOnProceedToCheckout();
    }

    @And("User clicks on Register on Checkout Alert")
    public void userClicksOnRegisterOnCheckout() {
        cart.userChecksRegisterOnCheckout();
    }

    @When("User verifies details on Checkout Page with {string}, {string}, {string} and {string}")
    public void userVerifiesCheckoutPageDetails(String name, String address, String country, String phone) {
        BaseActions.checkForAlert();
        cart.validateCheckoutDetails(name, address, country, phone);
    }

    @Test(dataProvider = "checkoutDetails")
    @When("User verifies details on Checkout Page via DataProvider")
    public void userVerifiesDetailsOnCheckoutPageViaDataProvider(HashMap<String, String> checkoutDetails) {
        cart.validateCheckoutDetails(checkoutDetails);
    }

    @DataProvider(name = "checkoutDetails")
    public Object[][] getData() {
        HashMap<String, String> checkoutDetailsI = new HashMap<>();
        checkoutDetailsI.put("products", "Blue Top, Winter Top");
        checkoutDetailsI.put("email", "jdough@gmail.com");
        checkoutDetailsI.put("password", "j%hnD*ug!");
        checkoutDetailsI.put("name", "Mr. John Dough");
        checkoutDetailsI.put("address", "Dough Imports & Exports");
        checkoutDetailsI.put("country", "United States");
        checkoutDetailsI.put("phone", "9798998888");

        HashMap<String, String> checkoutDetailsII = new HashMap<>();
        checkoutDetailsII.put("products", "Blue Top, Winter Top");
        checkoutDetailsII.put("email", "mjain@gmail.com");
        checkoutDetailsII.put("password", "M@ryJ$!n");
        checkoutDetailsII.put("name", "Mrs. Mary Jain");
        checkoutDetailsII.put("address", "Mary's Cookies");
        checkoutDetailsII.put("country", "Philippines");
        checkoutDetailsII.put("phone", "9798998889");

        return new Object[][]{
                {checkoutDetailsI},
//              {checkoutDetailsII}
        };
    }

    @And("User enters the following comment and places order:")
    public void userEntersCommentAndPlacesOrder(String comment) {
        cart.userAddsCommentUnderProduct(comment);
        cart.userPlacesOrder();
    }

    @And("User enters payment information with {string}, {string}, {string}, {string}, {string}")
    public void userEntersPaymentInformationWithDetails(String name, String cardNumber, String cvc, String expiryNumber, String expiryYear) {
        paymentPage.enterCardDetails(name, cardNumber, cvc, expiryNumber, expiryYear);
        paymentPage.placeOrder();
    }

    @And("User confirms order placement")
    public void userConfirmsOrderPlacement() {
        paymentPage.userConfirmsOrder();
    }

    @When("User adds products to cart via DataProvider")
    public void userAddsProductsToCartViaDataProvider() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("User clears cart completely")
    public void userClearsCartCompletely() {
        cart.clearCartCompletely();
    }
}
