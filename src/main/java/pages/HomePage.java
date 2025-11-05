package pages;

import interfaces.HomePageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.BaseActions;
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
        System.out.printf("Selecting " + category + " on the Home Page");
        BaseActions.click(By.xpath(HomePageLocators.GENDER_SPECIFIC_CATEGORY_XPATH(href)));
    }

    public void userObservesProducts(String products) {
        List<WebElement> productNames = BaseActions.findAll(By.xpath(HomePageLocators.PRODUCTS_LIST));
        for(WebElement product : productNames) {
            System.out.print("\n" + product.getText());
        }

        ProductUtils.printProductComparison(products, productNames);
    }

    private WebElement getProductContainer(String productName) {
        return BaseActions.find(By.xpath(HomePageLocators.PRODUCT_SPECIFIC_CONTAINER(productName)));
    }

    public void clickOnViewProduct(String productName) {
        BaseActions.click(getProductContainer(productName), By.xpath(HomePageLocators.VIEW_PRODUCT));
    }
}
