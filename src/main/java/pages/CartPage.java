package pages;

import interfaces.CartPageLocators;
import interfaces.ProductPageLocators;
import interfaces.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Assertions;
import utils.BaseActions;
import utils.Logger;
import utils.WaitUtils;

import java.util.HashMap;
import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
        Logger.info("CartPage initialized successfully");
    }

    WebElement productRow;

    public void userRemovesProduct(String toBeRemovedProduct) {
        Logger.info("Removing product from cart: {}", toBeRemovedProduct);
        productRow = getDriver().findElement(By.xpath(CartPageLocators.PRODUCT_SPECIFIC_ROW(toBeRemovedProduct)));
        Logger.debug("Product row found for: {}", toBeRemovedProduct);
        BaseActions.click(productRow, By.xpath(CartPageLocators.DELETE_PRODUCT));
        Logger.info("Deleted {} from the Cart", toBeRemovedProduct);

        // Wait for the product row to be removed from the DOM
        WaitUtils.waitUntilElementNotPresent(getDriver(), By.xpath(CartPageLocators.PRODUCT_SPECIFIC_ROW(toBeRemovedProduct)));
        Logger.debug("Product {} successfully removed from cart", toBeRemovedProduct);
    }

    public void userVerifiesCartContainingProducts(String products) {
        Logger.info("The expected products in the cart are: {}", products);
        WaitUtils.waitForSeconds(getDriver(), 2);

        List<WebElement> productsInCart = BaseActions.findAll(By.xpath(ProductPageLocators.PRODUCT_DETAILS));
        Logger.info("The number of elements in the cart are: {}", productsInCart.size());
        for(WebElement product : productsInCart){
            Logger.info("The product is: {}", product.getText());
            Assertions.assertMessageContains(products, product.getText());
        }
    }

    public void userChecksRegisterOnCheckout() {
        Logger.info("Checking register/login on checkout");
        WebElement loginLink = BaseActions.find(By.xpath(CartPageLocators.ALERT_LOGIN_BUTTON));
        Logger.debug("Login link found on checkout page");
        WaitUtils.waitUntilClickable(getDriver(), loginLink);
        BaseActions.click(loginLink);
        Logger.debug("Login link clicked");
        WaitUtils.waitUntilURLContains(getDriver(), URLs.LOGIN_PAGE);
        Logger.info("Successfully navigated to login page from checkout");
    }

    public void validateCheckoutDetails(String name, String address, String country, String phone) {
        Logger.info("Validating checkout details - Name: {}, Country: {}, Phone: {}", name, country, phone);
        WebElement deliveryAddressDetailBox = getDriver().findElement(By.xpath(CartPageLocators.ADDRESS_BOX));
        Logger.debug("Address box located for validation");
        Assertions.assertTextInElementContains(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_NAME)), name);
        Logger.debug("Name validation passed: {}", name);
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_ADDRESS)), address);
        Logger.debug("Address validation passed");
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_COUNTRY)), country);
        Logger.debug("Country validation passed: {}", country);
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_PHONE)), phone);
        Logger.debug("Phone validation passed: {}", phone);
        Logger.info("Checkout details validation completed successfully");
    }

    public void validateCheckoutDetails(HashMap<String, String> details) {
        Logger.info("Validating checkout details using HashMap with {} entries", details.size());
        WebElement deliveryAddressDetailBox = getDriver().findElement(By.xpath(CartPageLocators.ADDRESS_BOX));
        Logger.debug("Address box located for HashMap validation");
        Assertions.assertTextInElementContains(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_NAME)), details.get("name"));
        Logger.debug("Name validation passed: {}", details.get("name"));
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_ADDRESS)), details.get("address"));
        Logger.debug("Address validation passed");
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_COUNTRY)), details.get("country"));
        Logger.debug("Country validation passed: {}", details.get("country"));
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_PHONE)), details.get("phone"));
        Logger.debug("Phone validation passed: {}", details.get("phone"));
        Logger.info("HashMap-based checkout details validation completed successfully");
    }

    public void userAddsCommentUnderProduct(String comment) {
        Logger.info("Adding comment under product: {}", comment);
        BaseActions.enterIntoElement(By.xpath(CartPageLocators.COMMENT_SECTION), comment);
        Logger.debug("Comment added successfully: {}", comment);
    }

    public void userPlacesOrder() {
        Logger.info("Placing order by clicking place order button");
        BaseActions.click(By.xpath(CartPageLocators.PLACE_ORDER_BUTTON));
        Logger.debug("Place order button clicked");
        Assertions.assertCurrentUrl(getDriver(), URLs.PAYMENT_PAGE);
        Logger.info("Order placed successfully, navigated to payment page");
    }

    public void clearCartCompletely() {
        Logger.info("Starting to clear cart completely");

        List<WebElement> deleteButtons = BaseActions.findAll(By.xpath(CartPageLocators.DELETE_PRODUCT));
        int initialCount = deleteButtons.size();

        Logger.info("Found {} products in cart to delete", initialCount);

        for (int i = 0; i < initialCount; i++) {
            // Re-find delete buttons each time to avoid stale elements
            deleteButtons = BaseActions.findAll(By.xpath(CartPageLocators.DELETE_PRODUCT));

            if (deleteButtons.isEmpty()) {
                Logger.info("No more delete buttons found after {} deletions", i);
                break;
            }

            Logger.info("Deleting product {} of {}", i + 1, initialCount);
            deleteButtons.get(0).click();

            // Wait for the product row to be removed from DOM
            int expectedCount = deleteButtons.size() - 1;
            WaitUtils.waitForSeconds(getDriver(), 1);

            // Wait until the count decreases or timeout
            for (int retries = 0; retries < 10; retries++) {
                List<WebElement> currentButtons = BaseActions.findAll(By.xpath(CartPageLocators.DELETE_PRODUCT));
                if (currentButtons.size() == expectedCount) {
                    break;
                }
                WaitUtils.waitForSeconds(getDriver(), 1);
            }
        }

        Logger.info("Cart clearing completed");
        verifyCartIsEmpty();
    }

    private void verifyCartIsEmpty() {
        Logger.debug("Verifying cart is empty");
        String actualText = BaseActions.getTextOfElement(By.xpath(CartPageLocators.CART_EMPTY_LOCATOR));
        if (!actualText.contains(CartPageLocators.CART_EMPTY_MESSAGE)) {
            Logger.warn("Cart verification failed. Expected message containing '{}', but found '{}'",
                       CartPageLocators.CART_EMPTY_MESSAGE, actualText);
        } else {
            Logger.info("Cart verification successful: Cart is empty");
        }
    }
}
