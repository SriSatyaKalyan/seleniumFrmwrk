package pages;

import interfaces.HomePageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.BaseActions;

public class HeaderSection extends BasePage{
    public HeaderSection(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
    }

    private WebElement getHeaderSection() {
        return BaseActions.find(By.xpath(HomePageLocators.HEADER_SECTION));
    }

    public void clickProductsOption() {
        getHeaderSection()
                .findElement(By.xpath(HomePageLocators.HEADER_PRODUCTS))
                .click();
    }

    public void clickCartOption() {
        getHeaderSection()
                .findElement(By.xpath(HomePageLocators.HEADER_CART))
                .click();
    }

    public void clickTestCasesOption() {
        getHeaderSection()
                .findElement(By.xpath(HomePageLocators.HEADER_TESTCASES))
                .click();
    }

    public void clickApiTestingOption() {
        getHeaderSection()
                .findElement(By.xpath(HomePageLocators.HEADER_APILIST))
                .click();
    }

    public void clickVideoTutorialsOption() {
        getHeaderSection()
                .findElement(By.xpath(HomePageLocators.HEADER_VIDEOTUTORIALS))
                .click();
    }

    public void clickContactUsOption() {
        getHeaderSection()
                .findElement(By.xpath(HomePageLocators.HEADER_CONTACTUS))
                .click();
    }
}
