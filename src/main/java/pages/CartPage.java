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

import java.util.HashMap;
import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
    }

    WebElement productRow;

    public void userRemovesProduct(String toBeRemovedProduct) {
        productRow = getDriver().findElement(By.xpath(CartPageLocators.PRODUCT_SPECIFIC_ROW(toBeRemovedProduct)));
        BaseActions.click(productRow, By.xpath(CartPageLocators.DELETE_PRODUCT));
        Logger.info("{}", "Deleted " + toBeRemovedProduct + " from the Cart");

        // Wait for the product row to be removed from the DOM
        BaseActions.waitUntilElementNotPresent(By.xpath(CartPageLocators.PRODUCT_SPECIFIC_ROW(toBeRemovedProduct)));
    }

    public void userVerifiesCartContainingProducts(String products) {
        Logger.info("The expected products in the cart are: {}", products);
        BaseActions.waitForSeconds(2);

        List<WebElement> productsInCart = BaseActions.findAll(By.xpath(ProductPageLocators.PRODUCT_DETAILS));
        Logger.info("The number of elements in the cart are: {}", productsInCart.size());
        for(WebElement product : productsInCart){
            Logger.info("The product is: {}", product.getText());
            Assertions.assertMessageContains(products, product.getText());
        }
    }

    public void userChecksRegisterOnCheckout() {
        WebElement loginLink = BaseActions.find(By.xpath(CartPageLocators.ALERT_LOGIN_BUTTON));
        BaseActions.waitUntilClickable(loginLink);
        BaseActions.click(loginLink);
        BaseActions.waitUntilURLContains(URLs.LOGIN_PAGE);
    }

    public void validateCheckoutDetails(String name, String address, String country, String phone) {
        WebElement deliveryAddressDetailBox = getDriver().findElement(By.xpath(CartPageLocators.ADDRESS_BOX));
        Assertions.assertTextInElementContains(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_NAME)), name);
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_ADDRESS)), address);
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_COUNTRY)), country);
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_PHONE)), phone);
    }

    public void validateCheckoutDetails(HashMap<String, String> details) {
        WebElement deliveryAddressDetailBox = getDriver().findElement(By.xpath(CartPageLocators.ADDRESS_BOX));
        Assertions.assertTextInElementContains(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_NAME)), details.get("name"));
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_ADDRESS)), details.get("address"));
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_COUNTRY)), details.get("country"));
        Assertions.assertTextInElement(BaseActions.find(deliveryAddressDetailBox, By.xpath(CartPageLocators.ADDRESS_BOX_PHONE)), details.get("phone"));
    }

    public void userAddsCommentUnderProduct(String comment) {
        BaseActions.enterIntoElement(By.xpath(CartPageLocators.COMMENT_SECTION), comment);
    }

    public void userPlacesOrder() {
        BaseActions.click(By.xpath(CartPageLocators.PLACE_ORDER_BUTTON));
        Assertions.assertCurrentUrl(getDriver(), URLs.PAYMENT_PAGE);
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
            BaseActions.waitForSeconds(1);

            // Wait until the count decreases or timeout
            for (int retries = 0; retries < 10; retries++) {
                List<WebElement> currentButtons = BaseActions.findAll(By.xpath(CartPageLocators.DELETE_PRODUCT));
                if (currentButtons.size() == expectedCount) {
                    break;
                }
                BaseActions.waitForSeconds(1);
            }
        }

        Logger.info("Cart clearing completed");
        verifyCartIsEmpty();
    }

    private void verifyCartIsEmpty() {
        String actualText = BaseActions.getTextOfElement(By.xpath(CartPageLocators.CART_EMPTY_LOCATOR));
        if (!actualText.contains(CartPageLocators.CART_EMPTY_MESSAGE)) {
            Logger.info("Cart verification failed. Expected message containing '{}', but found '{}'",
                       CartPageLocators.CART_EMPTY_MESSAGE, actualText);
        } else {
            Logger.info("Cart verification successful: Cart is empty");
        }
    }
}
