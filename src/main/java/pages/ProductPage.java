package pages;

import interfaces.ProductPageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Assertions;
import utils.BaseActions;
import utils.Logger;
import utils.WaitUtils;
import java.util.List;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
        Logger.info("ProductPage initialized successfully");
    }

    WebElement addToCartNotification;

    public void addNumberProductsToCart(String number) {
        Logger.info("Adding {} products to cart", number);
        int numOfItems = Integer.parseInt(number);
        for (int i = 0; i < numOfItems; i++) {
            Logger.debug("Adding product {} of {}", i + 1, numOfItems);
            BaseActions.click(By.xpath(ProductPageLocators.PRODUCT_ADD_TO_CART));
            addToCartNotification = BaseActions.find(By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION));
            Logger.info("Notification: ", addToCartNotification, By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION_TEXT));
            BaseActions.click(addToCartNotification, By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION_MODAL_CLOSE));
            Logger.debug("Product {} added successfully", i + 1);
        }
        Logger.info("{} products added to cart successfully", number);
    }

    WebElement productRow;
    WebElement quantityElement;

    public void verifyNumberProductsInCart(String product, String number) {
        Logger.info("Verifying {} quantity for product: {}", number, product);
        productRow = BaseActions.find(By.xpath(ProductPageLocators.PRODUCT_ROW(product)));
        Logger.debug("Product row located for: {}", product);
        quantityElement = BaseActions.find(productRow, By.xpath(ProductPageLocators.PRODUCT_ROW_QUANTITY_ELEMENT));
        Logger.debug("Quantity element located");
        Assertions.assertQuantityInElement(quantityElement, number);
        Logger.debug("Product quantity verification completed successfully");
    }

    public void addProductstoCart(String products) {
        Logger.info("Adding multiple products to cart: {}", products);
        List<String> productList = List.of(products.split(","));
        Logger.debug("Processing {} products", productList.size());
        for (String product : productList) {
            Logger.info("Adding product to cart: {}", product.trim());
            addProductToCart(product.trim());
        }
        Logger.info("All products added to cart successfully");
    }

    WebElement productContainer;
    WebElement addToCartButton;

    public void addProductToCart(String productName) {
        Logger.info("Adding individual product to cart: {}", productName);
        productContainer = BaseActions.find(By.xpath(ProductPageLocators.PRODUCT_CONTAINER(productName)));
        Logger.debug("Product container found for: {}", productName);
        BaseActions.scrollIntoView(productContainer);
        Logger.debug("Scrolled to product container");

        addToCartButton = BaseActions.find(productContainer, By.xpath(ProductPageLocators.PRODUCT_CONTAINER_ADD_TO_CART));
        Logger.debug("Add to cart button located");
        BaseActions.moveToElement(addToCartButton);
        WaitUtils.waitUntilVisible(getDriver(), addToCartButton);
        Logger.debug("Moved to add to cart button and waited for visibility");

        boolean isClickable = false;
        int maxAttempts = 10;
        int attempts = 0;
        Logger.debug("Starting click attempts for add to cart button");

        while (!isClickable && attempts < maxAttempts) {
            try {
                Logger.debug("Attempt {} to click add to cart button", attempts + 1);
                // Scroll button into view
                BaseActions.scrollIntoView(addToCartButton);
                WaitUtils.waitUntilVisible(getDriver(), addToCartButton);
                WaitUtils.waitUntilClickable(getDriver(), addToCartButton);

                // Try JavaScript click to bypass ad interference
                JavascriptExecutor js = (JavascriptExecutor) BaseActions.getDriver();
                js.executeScript("arguments[0].click();", addToCartButton);
                isClickable = true;
                Logger.debug("Add to cart button clicked successfully");
            } catch (Exception e) {
                Logger.warn("Click attempt {} failed: {}", attempts + 1, e.getMessage());
                BaseActions.scrollDown();
                attempts++;
                WaitUtils.waitForSeconds(getDriver(), 1);
            }
        }

        if (!isClickable) {
            Logger.error("Add to cart button could not be clicked after {} attempts for product: {}", maxAttempts, productName);
            throw new RuntimeException("Button could not be made clickable after " + maxAttempts + " attempts");
        }

        addToCartNotification = BaseActions.find(By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION));
        Logger.info("Notification: ", addToCartNotification, By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION_TEXT));
        BaseActions.click(addToCartNotification, By.xpath(ProductPageLocators.ADD_TO_CART_NOTIFICATION_MODAL_CLOSE));
        Logger.debug("Add to cart notification modal closed");
        Logger.info("Product {} successfully added to cart", productName);
    }

    public void userClicksOnProceedToCheckout() {
        Logger.info("Clicking on Proceed to Checkout button");
        BaseActions.click(By.xpath(ProductPageLocators.PRODUCT_CHECKOUT_BUTTON));
        Logger.debug("Proceed to checkout button clicked successfully");
    }

    public void observeProductsDeets(String name, String category, String cost, String availability, String condition, String brand) {
        Logger.info("Observing product details - Name: {}, Category: {}, Cost: {}, Brand: {}", name, category, cost, brand);
        WaitUtils.waitUntilVisible(getDriver(), By.xpath(ProductPageLocators.PRODUCT_INFORMATION));
        WebElement productInfo = BaseActions.find(By.xpath(ProductPageLocators.PRODUCT_INFORMATION));
        Logger.debug("Product information section located");

        WebElement categoryElement = BaseActions.find(productInfo, By.xpath(ProductPageLocators.PRODUCT_CATEGORY_ELEMENT(category)));
        Assertions.assertDisplayedWithMessage(categoryElement, "Category '" + category + "' not displayed");
        Logger.debug("Category validation passed: {}", category);

        WebElement costElement = BaseActions.find(productInfo, By.xpath(ProductPageLocators.PRODUCT_COST_ELEMENT(cost)));
        Assertions.assertDisplayedWithMessage(costElement, "Cost 'Rs. " + cost + "' not displayed");
        Logger.debug("Cost validation passed: Rs. {}", cost);

        WebElement availabilityElement = BaseActions.find(productInfo, By.xpath(ProductPageLocators.PRODUCT_AVAILABILITY_ELEMENT(availability)));
        Assertions.assertDisplayedWithMessage(availabilityElement, "Availability '" + availability + "' not displayed");
        Logger.debug("Availability validation passed: {}", availability);

        WebElement conditionElement = BaseActions.find(productInfo, By.xpath(ProductPageLocators.PRODUCT_CONDITION_ELEMENT(condition)));
        Assertions.assertDisplayedWithMessage(conditionElement, "Condition '" + condition + "' not displayed");
        Logger.debug("Condition validation passed: {}", condition);

        WebElement brandElement = BaseActions.find(productInfo, By.xpath(ProductPageLocators.PRODUCT_BRAND_ELEMENT(brand)));
        Assertions.assertDisplayedWithMessage(brandElement, "Brand '" + brand + "' not displayed");
        Logger.debug("Brand validation passed: {}", brand);
        Logger.info("Product details observation completed successfully");
    }

    public void observeReviewSection() {
        Logger.info("Observing product review section");
        WebElement reviewForm = BaseActions.find(By.xpath(ProductPageLocators.REVIEW_FORM));
        Logger.debug("Review form element located");
        Assertions.assertDisplayedWithMessage(reviewForm, "Review section not found");
        Logger.debug("Review section observation completed successfully");
    }
}