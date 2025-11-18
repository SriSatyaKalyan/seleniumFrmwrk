package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.*;
import pages.CartPage;
import pages.LoginPage;
import pages.PaymentPage;
import pages.ProductPage;
import utils.BaseActions;

import utils.DataManager;
import utils.TestDataResolver;

public class aeCartPage {

    // TODO: Needs to be modified. No sout's
    public ProductPage productPage = new ProductPage(getDriver());
    public CartPage cart = new CartPage(getDriver());
    public PaymentPage paymentPage = new PaymentPage(getDriver());
    public LoginPage loginPage = new LoginPage(getDriver());

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

    @Then("User clears cart completely")
    public void userClearsCartCompletely() {
        cart.clearCartCompletely();
    }

    @When("User adds products from data {string} to cart")
    public void userAddsProductsFromDataToCart(String productKey) {
        String products = TestDataResolver.resolveProductsFromData(productKey);
        productPage.addProductstoCart(products);
    }

    @Then("User observes Cart contains products from data {string}")
    public void userObservesCartContainsProductsFromData(String productKey) {
        String products = TestDataResolver.resolveProductsFromData(productKey);
        cart.userVerifiesCartContainingProducts(products);
    }

    @When("User enters credentials from data {string}")
    public void userEntersCredentialsFromData(String userKey) {
        DataManager.UserData userData = TestDataResolver.resolveUserFromData(userKey);
        loginPage.enterLoginDetails(userData.getEmail(), userData.getPassword());
    }

    @When("User verifies details on Checkout Page from data {string}")
    public void userVerifiesDetailsOnCheckoutPageFromData(String userKey) {
        DataManager.UserData userData = TestDataResolver.resolveUserFromData(userKey);
        BaseActions.checkForAlert();
        cart.validateCheckoutDetails(
            TestDataResolver.formatUserNameWithTitle(userData),
            userData.getCompany(),
            userData.getCountry(),
            userData.getMobileNumber()
        );
    }
}
