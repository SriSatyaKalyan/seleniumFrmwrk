package stepDefinitions;

import interfaces.ProductPageLocators;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.ProductPage;
import pages.SignUpPage;
import utils.BaseActions;
import utils.Logger;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class aeCartPage {

    // TODO: Needs to be modified. No sout's
    public ProductPage productPage = new ProductPage(getDriver());
    public CartPage cart = new CartPage(getDriver());

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
    public void userClicksOnOptionOnRegisterOnCheckoutAlert() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='modal-content']")));
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='modal-content']//a[@href='/login']")));
        loginLink.click();
        wait.until(ExpectedConditions.urlContains("www.automationexercise.com/login"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("www.automationexercise.com/login"));
    }

    @When("User verifies delivery address on Checkout Page with {string}, {string}, {string} and {string}")
    public void userVerifiesAddressesOnCheckoutPageWithDetails(String name, String address, String country, String phone) {
        try {
            // Handle any browser alerts
            Alert alert = getDriver().switchTo().alert();
            alert.dismiss();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present");
        }

        WebElement deliveryAddressDetailBox = getDriver().findElement(By.xpath("//ul[@class='address item box']"));
        Assert.assertTrue(deliveryAddressDetailBox.findElement(By.xpath("//li[@class='address_firstname address_lastname']")).getText().contains(name));
        Assert.assertTrue(deliveryAddressDetailBox.findElement(By.xpath("//li[@class='address_address1 address_address2']")).getText().contains(address));
        Assert.assertTrue(deliveryAddressDetailBox.findElement(By.xpath("//li[@class='address_country_name']")).getText().contains(country));
        Assert.assertTrue(deliveryAddressDetailBox.findElement(By.xpath("//li[@class='address_phone']")).getText().contains(phone));
    }

    @And("User enters the following comment and places order:")
    public void userEntersTheFollowingCommentAndPlacesOrder(String comment) {
        getDriver().findElement(By.xpath("//textarea[@name='message']")).sendKeys(comment);
        getDriver().findElement(By.xpath("//a[@href='/payment']")).click();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("https://www.automationexercise.com/payment"));
    }

    @And("User enters payment information")
    public void userEntersPaymentInformation() throws InterruptedException {
//        getDriver().findElement(By.xpath("//textarea[@name='message']")).sendKeys(comment);
//        getDriver().findElement(By.xpath("//a[@href='/payment']")).click();
//        Assert.assertTrue(getDriver().getCurrentUrl().contains("https://www.automationexercise.com/payment"));

        WebElement paymentForm = getDriver().findElement(By.xpath("//form[@id='payment-form']"));

        WebElement nameOnCard = paymentForm.findElement(By.xpath("//input[@name='name_on_card']"));
        nameOnCard.sendKeys("John Dough");
        WebElement cardNumber = paymentForm.findElement(By.xpath("//input[@name='card_number']"));
        cardNumber.sendKeys("1234 1234 1234 1234");
        WebElement cvc = paymentForm.findElement(By.xpath("//input[@class='form-control card-cvc']"));
        cvc.sendKeys("311");
        WebElement expiryMonth = paymentForm.findElement(By.xpath("//input[@class='form-control card-expiry-month']"));
        expiryMonth.sendKeys("09");
        WebElement expiryYear = paymentForm.findElement(By.xpath("//input[@class='form-control card-expiry-year']"));
        expiryYear.sendKeys("2030");

        getDriver().findElement(By.xpath("//button[@data-qa='pay-button']")).click();

        WebElement successAlert = getDriver().findElement(By.xpath("(//div[@class='alert-success alert'])[1]"));
        System.out.println("The success alert is: " + successAlert.getText());
        Assert.assertTrue(successAlert.getText().contains("Your order has been placed successfully!"));
    }

    @And("User confirms order placement")
    public void userConfirmsOrderPlacement() {
        Wait<WebDriver> fluentWait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        fluentWait.until(driver -> driver.getCurrentUrl().contains("payment_done"));

        Assert.assertTrue(Objects.requireNonNull(getDriver().getCurrentUrl()).contains("https://www.automationexercise.com/payment_done"));
        String orderPlacement = getDriver().findElement(By.xpath("//*[@id='form']/div/div/div/p")).getText();
        System.out.println(orderPlacement);
    }

    @And("User enters comment {string} and places order")
    public void userEntersCommentCommentAndPlacesOrder(String comment) {
        getDriver().findElement(By.xpath("//textarea[@name='message']")).sendKeys(comment);
        getDriver().findElement(By.xpath("//a[@href='/payment']")).click();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("https://www.automationexercise.com/payment"));
    }
}
