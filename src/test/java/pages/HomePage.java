package pages;

import interfaces.HomePageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.BaseActions;
import utils.Logger;
import utils.ProductUtils;
import java.util.List;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
        Logger.info("HomePage initialized successfully");
    }

    public void clickWomenCategory() {
        Logger.info("Clicking on Women category");
        BaseActions.click(By.xpath(HomePageLocators.WOMEN_CATEGORY_XPATH));
        Logger.debug("Women category clicked successfully");
    }

    public void clickMenCategory() {
        Logger.info("Clicking on Men category");
        BaseActions.click(By.xpath(HomePageLocators.MEN_CATEGORY_XPATH));
        Logger.debug("Men category clicked successfully");
    }

    public void selectGenderSpecificCategory(String category, String href) {
        Logger.info("Selecting {} on the Home Page with href: {}", category, href);
        BaseActions.click(By.xpath(HomePageLocators.GENDER_SPECIFIC_CATEGORY_XPATH(href)));
        Logger.debug("Gender specific category {} selected successfully", category);
    }

    public void userObservesProducts(String products) {
        Logger.info("Observing products on the page. Expected products: {}", products);
        List<WebElement> productNames = BaseActions.findAll(By.xpath(HomePageLocators.PRODUCTS_LIST));
        Logger.debug("Found {} products on the page", productNames.size());
        for(WebElement product : productNames) {
            Logger.info("Product found: {}", product.getText());
        }

        ProductUtils.printProductComparison(products, productNames);
        Logger.debug("Product observation completed");
    }

    private WebElement getProductContainer(String productName) {
        Logger.debug("Getting product container for: {}", productName);
        WebElement productContainer = BaseActions.find(By.xpath(HomePageLocators.PRODUCT_SPECIFIC_CONTAINER(productName)));
        Logger.debug("Product container found for: {}", productName);
        return productContainer;
    }

    public void clickOnViewProduct(String productName) {
        Logger.info("Clicking on 'View Product' for: {}", productName);
        BaseActions.click(getProductContainer(productName), By.xpath(HomePageLocators.VIEW_PRODUCT));
        Logger.debug("View Product clicked successfully for: {}", productName);
    }

    public void bringProductIntoView(String productName) {
        Logger.info("Bringing product into view: {}", productName);
        WebElement productContainer = getProductContainer(productName);
        BaseActions.scrollDown(); // Needed for Cart scenarios
        Logger.debug("Scrolled down for product: {}", productName);
        BaseActions.scrollIntoView(productContainer);
        Logger.debug("Product {} brought into view successfully", productName);
    }

    public void searchForProduct(String product) {
        Logger.info("Searching for product: {}", product);
        BaseActions.enterIntoElement(By.xpath(HomePageLocators.SEARCH_PRODUCT_INPUT), product);
        Logger.debug("Product name entered in search box: {}", product);
        BaseActions.click(By.xpath(HomePageLocators.SEARCH_SUBMIT_BUTTON));
        Logger.debug("Search button clicked for product: {}", product);
    }
}
