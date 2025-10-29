package pages;

import interfaces.HomePageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseStepDefinitions;

public class HeaderSection extends BasePage{
    public HeaderSection(WebDriver driver) {
        super(driver);
        BaseStepDefinitions.setDriver(driver);
    }

    public void clickProductsOption() {
        BaseStepDefinitions
                .find(By.xpath(HomePageLocators.HEADER_SECTION))
                .findElement(By.xpath(HomePageLocators.HEADER_PRODUCTS))
                .click();
    }
}
