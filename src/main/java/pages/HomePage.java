package pages;

import interfaces.HomePageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.BaseStepDefinitions;
import utils.ProductUtils;

import java.util.List;
//import stepDefinitions.testBase;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver) {
        super(driver);
        BaseStepDefinitions.setDriver(driver);
    }

    public void clickWomenCategory() {
        BaseStepDefinitions.click(By.xpath(HomePageLocators.WOMEN_CATEGORY_XPATH));
    }

    public void clickMenCategory() {
        BaseStepDefinitions.click(By.xpath(HomePageLocators.MEN_CATEGORY_XPATH));
    }

    public void selectGenderSpecificCategory(String category, String href) {
        System.out.printf("Selecting " + category + " on the Home Page");
        BaseStepDefinitions.click(By.xpath(HomePageLocators.GENDER_SPECIFIC_CATEGORY_XPATH(href)));
//        getDriver().findElement(By.xpath("//a[@href='" + href + "']")).click();
    }

    public void userObservesProducts(String products) {
        List<WebElement> productNames = BaseStepDefinitions.findAll(By.xpath(HomePageLocators.PRODUCTS_LIST));
//                BaseStepDefinitions.findElements(By.xpath(HomePageLocators.PRODUCTS_LIST));
        for(WebElement product : productNames) {
            System.out.print("\n" + product.getText());
        }

        ProductUtils.printProductComparison(products, productNames);
    }
}
