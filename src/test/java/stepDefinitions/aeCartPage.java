package stepDefinitions;

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

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class aeCartPage {

    aeHome home = new aeHome();
    aeLogin login = new aeLogin();

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @And("User clicks on Add To Cart {string} times")
    public void userClicksOnAddToCartNumberTimes(String number) {
        int numOfItems = Integer.parseInt(number);
        for(int i = 0; i < numOfItems; i++){
            getDriver().findElement(By.xpath("//button[@class='btn btn-default cart']")).click();
            WebElement notification = getDriver().findElement(By.xpath("//div[@class='modal-content']"));
            System.out.println(notification.findElement(By.xpath("(//div[@class='modal-body']//p[@class='text-center'])[1]")).getText());
//            Assert.assertTrue(notification.findElement(By.xpath("(//div[@class='modal-body']//p[@class='text-center'])[1]")).getText().contains("Your product has been added to cart"));
            notification.findElement(By.xpath("//button[contains(@class, 'close-modal')]")).click();
        }
    }

    @Then("User observes Cart contains {string} {string} times")
    public void userObservesCartContainsProductNumberTimes(String product, String number) {
        WebElement productRow = getDriver().findElement(By.xpath("//tr[.//td[@class='cart_description']//a[text()='" + product + "']]"));

        // Find the quantity within that specific row
        WebElement quantityElement = productRow.findElement(By.xpath(".//td[@class='cart_quantity']/button"));
        String actualQuantity = quantityElement.getText();
        Assert.assertEquals(Integer.parseInt(quantityElement.getText()), Integer.parseInt(number));
    }

    @When("User adds {string} to cart")
    public void userAddsProductsToCart(String products) {
        List<String> productList = List.of(products.split(","));
        for(String product : productList){
            System.out.println("Adding product to cart: " + product.trim());
            addProductToCart(product.trim());
        }
    }

    public void addProductToCart(String productName) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        // Find the product container by name
        WebElement productContainer = getDriver().findElement(By.xpath("//div[@class='product-image-wrapper'][.//p[text()='" + productName + "']]"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", productContainer);

        // Click the "Add to cart" button within that specific product (use the first one in productinfo)
        WebElement addToCartButton = productContainer.findElement(By.xpath(".//div[@class='productinfo text-center']//a[contains(@class,'add-to-cart')]"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(addToCartButton).perform();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(1));
        js = (JavascriptExecutor) getDriver();
        boolean isClickable = false;
        int maxAttempts = 10;
        int attempts = 0;

        while (!isClickable && attempts < maxAttempts) {
            try {
                // Scroll button into view
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addToCartButton);
                Thread.sleep(500); // Allow smooth scroll to complete

                // Check if clickable
                wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
                isClickable = true;
            } catch (Exception e) {
                // If not clickable, scroll down more
                js.executeScript("window.scrollBy(0, 100);");
                attempts++;
            }
        }

        if (isClickable) {
            addToCartButton.click();
        } else {
            throw new RuntimeException("Button could not be made clickable after " + maxAttempts + " attempts");
        }

        // Handle the modal that appears
        WebElement notification = getDriver().findElement(By.xpath("//div[@class='modal-content']"));
        System.out.println("Cart notification: " + notification.findElement(By.xpath("//div[@class='modal-body']//p[@class='text-center']")).getText());

        // Close the modal
        notification.findElement(By.xpath("//button[contains(@class,'close-modal')]")).click();
    }

    @Then("User observes Cart contains {string}")
    public void userObservesCartContainsProducts(String products) {
        System.out.println("The expected products in the cart are: " + products);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        List<WebElement> productsInCart = getDriver().findElements(By.xpath("//a[contains(@href, '/product_details/')]"));
        System.out.println("The number of elements in the cart are: " + productsInCart.size());
        for(WebElement product : productsInCart){
            System.out.println("The product is: " + product.getText());
            Assert.assertTrue(products.contains(product.getText()));
        }
    }

    @When("User removes {string} from Cart")
    public void userRemovesToBeRemovedProductFromCart(String toBeRemovedProduct) {
        WebElement productRow = getDriver().findElement(By.xpath("//tr[.//td[@class='cart_description']//a[text()='" + toBeRemovedProduct + "']]"));

        // Find the quantity within that specific row
        WebElement deleteActionElement = productRow.findElement(By.xpath(".//a[@class='cart_quantity_delete']"));
        deleteActionElement.click();
        System.out.println("Deleted " + toBeRemovedProduct + " from the Cart");
    }

    @When("User clicks on Proceed To Checkout")
    public void userClicksOnProceedToCheckout() {
        getDriver().findElement(By.xpath("//a[@class='btn btn-default check_out']")).click();
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
