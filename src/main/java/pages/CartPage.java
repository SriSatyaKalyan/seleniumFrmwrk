package pages;

import interfaces.CartPageLocators;
import interfaces.ProductPageLocators;
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
}
