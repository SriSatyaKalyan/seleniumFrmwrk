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
        Logger.info("The expected products in the cart are: ", products);
        BaseActions.waitForSeconds();

        List<WebElement> productsInCart = BaseActions.findAll(By.xpath(ProductPageLocators.PRODUCT_DETAILS));
        Logger.info("The number of elements in the cart are: " + productsInCart.size());
        for(WebElement product : productsInCart){
            System.out.println("The product is: " + product.getText());
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

    public void userAddsCommentUnderProduct(String comment) {
        BaseActions.enterIntoElement(By.xpath(CartPageLocators.COMMENT_SECTION), comment);
    }

    public void userPlacesOrder() {
        BaseActions.click(By.xpath(CartPageLocators.PLACE_ORDER_BUTTON));
        Assertions.assertCurrentUrl(getDriver(), URLs.PAYMENT_PAGE);
    }
}
