package pages;

import interfaces.HomePageLocators;
import org.jsoup.Connection;
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
    }

    public void clickWomenCategory() {
        BaseActions.click(By.xpath(HomePageLocators.WOMEN_CATEGORY_XPATH));
    }

    public void clickMenCategory() {
        BaseActions.click(By.xpath(HomePageLocators.MEN_CATEGORY_XPATH));
    }

    public void selectGenderSpecificCategory(String category, String href) {
        Logger.info("Selecting {} on the Home Page", category);
        BaseActions.click(By.xpath(HomePageLocators.GENDER_SPECIFIC_CATEGORY_XPATH(href)));
    }

    public void userObservesProducts(String products) {
        List<WebElement> productNames = BaseActions.findAll(By.xpath(HomePageLocators.PRODUCTS_LIST));
        for(WebElement product : productNames) {
            Logger.info("Product found: {}", product.getText());
        }

        ProductUtils.printProductComparison(products, productNames);
    }

    private WebElement getProductContainer(String productName) {
        return BaseActions.find(By.xpath(HomePageLocators.PRODUCT_SPECIFIC_CONTAINER(productName)));
    }

    public void clickOnViewProduct(String productName) {
        BaseActions.click(getProductContainer(productName), By.xpath(HomePageLocators.VIEW_PRODUCT));
    }

    public void bringProductIntoView(String productName) {
        WebElement productContainer = getProductContainer(productName);
        BaseActions.scrollDown(); // Needed for Cart scenarios
        BaseActions.scrollIntoView(productContainer);
    }
}
