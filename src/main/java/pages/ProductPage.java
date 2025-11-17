package pages;

import interfaces.HomePageLocators;
import interfaces.ProductPageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.Assertion;
import utils.Assertions;
import utils.BaseActions;
import utils.Logger;
import utils.WaitUtils;

import java.time.Duration;
import java.util.List;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
    }

    WebElement addToCartNotification;

    public void addNumberProductsToCart(String number) {
        int numOfItems = Integer.parseInt(number);
        for (int i = 0; i < numOfItems; i++) {
            BaseActions.click(By.xpath(ProductPageLocators.PRODUCT_ADD_TO_CART));
            addToCartNotification = BaseActions.find(By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION));
            Logger.info("Notification: ", addToCartNotification, By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION_TEXT));
            BaseActions.click(addToCartNotification, By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION_MODAL_CLOSE));
        }
    }

    WebElement productRow;
    WebElement quantityElement;

    public void verifyNumberProductsInCart(String product, String number) {
        productRow = BaseActions.find(By.xpath(ProductPageLocators.PRODUCT_ROW(product)));
        quantityElement = BaseActions.find(productRow, By.xpath(ProductPageLocators.PRODUCT_ROW_QUANTITY_ELEMENT));
        Assertions.assertQuantityInElement(quantityElement, number);
    }

    public void addProductstoCart(String products) {
        List<String> productList = List.of(products.split(","));
        for (String product : productList) {
            Logger.info("Adding product to cart: {}", product.trim());
            addProductToCart(product.trim());
        }
    }

    WebElement productContainer;
    WebElement addToCartButton;

    public void addProductToCart(String productName) {
        productContainer = BaseActions.find(By.xpath(ProductPageLocators.PRODUCT_CONTAINER(productName)));
        BaseActions.scrollIntoView(productContainer);

        addToCartButton = BaseActions.find(productContainer, By.xpath(ProductPageLocators.PRODUCT_CONTAINER_ADD_TO_CART));
        BaseActions.moveToElement(addToCartButton);
        WaitUtils.waitUntilVisible(getDriver(), addToCartButton);

        boolean isClickable = false;
        int maxAttempts = 10;
        int attempts = 0;

        while (!isClickable && attempts < maxAttempts) {
            try {
                // Scroll button into view
                BaseActions.scrollIntoView(addToCartButton);
                WaitUtils.waitUntilVisible(getDriver(), addToCartButton);
                WaitUtils.waitUntilClickable(getDriver(), addToCartButton);

                // Try JavaScript click to bypass ad interference
                JavascriptExecutor js = (JavascriptExecutor) BaseActions.getDriver();
                js.executeScript("arguments[0].click();", addToCartButton);
                isClickable = true;
            } catch (Exception e) {
                BaseActions.scrollDown();
                attempts++;
                WaitUtils.waitForSeconds(getDriver(), 1);
            }
        }

        if (!isClickable) {
            throw new RuntimeException("Button could not be made clickable after " + maxAttempts + " attempts");
        }

        addToCartNotification = BaseActions.find(By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION));
        Logger.info("Notification: ", addToCartNotification, By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION_TEXT));
        BaseActions.click(addToCartNotification, By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION_MODAL_CLOSE));
    }

    public void userClicksOnProceedToCheckout() {
        BaseActions.click(By.xpath(ProductPageLocators.PRODUCT_CHECKOUT_BUTTON));
    }

    public void observeProductsDeets(String name, String category, String cost, String availability, String condition, String brand) {
        WebElement productInfo = BaseActions.find(By.xpath(ProductPageLocators.PRODUCT_INFORMATION));

        WebElement categoryElement = BaseActions.find(productInfo, By.xpath(ProductPageLocators.PRODUCT_CATEGORY_ELEMENT(category)));
        Assertions.assertDisplayedWithMessage(categoryElement, "Category '" + category + "' not displayed");

        WebElement costElement = BaseActions.find(productInfo, By.xpath(ProductPageLocators.PRODUCT_COST_ELEMENT(cost)));
        Assertions.assertDisplayedWithMessage(costElement, "Cost 'Rs. " + cost + "' not displayed");

        WebElement availabilityElement = BaseActions.find(productInfo, By.xpath(ProductPageLocators.PRODUCT_AVAILABILITY_ELEMENT(availability)));
        Assertions.assertDisplayedWithMessage(availabilityElement, "Availability '" + availability + "' not displayed");

        WebElement conditionElement = BaseActions.find(productInfo, By.xpath(ProductPageLocators.PRODUCT_CONDITION_ELEMENT(condition)));
        Assertions.assertDisplayedWithMessage(conditionElement, "Condition '" + condition + "' not displayed");

        WebElement brandElement = BaseActions.find(productInfo, By.xpath(ProductPageLocators.PRODUCT_BRAND_ELEMENT(brand)));
        Assertions.assertDisplayedWithMessage(brandElement, "Brand '" + brand + "' not displayed");
    }

    public void observeReviewSection() {
        WebElement reviewForm = BaseActions.find(By.xpath(ProductPageLocators.REVIEW_FORM));
        Assertions.assertDisplayedWithMessage(reviewForm, "Review section not found");
    }
}