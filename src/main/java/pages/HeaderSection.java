package pages;

import interfaces.HomePageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.BaseActions;

public class HeaderSection extends BasePage {
    public HeaderSection(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
    }

    private WebElement getHeaderSection() {
        return BaseActions.find(By.xpath(HomePageLocators.HEADER_SECTION));
    }

    public void clickProductsOption() {
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_PRODUCTS));
    }

    public void clickCartOption() {
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_CART));
    }

    public void clickTestCasesOption() {
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_TESTCASES));
    }

    public void clickApiTestingOption() {
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_APILIST));
    }

    public void clickVideoTutorialsOption() {
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_VIDEOTUTORIALS));
    }

    public void clickContactUsOption() {
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_CONTACTUS));
    }

    public void clickLogOutOption() {
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_LOGOUT));
    }

    public void validateLoggedInAsText() {
        BaseActions.printTextOfElement(By.xpath(HomePageLocators.HEADER_LOGGED_IN_TEXT));
    }

    public void clickDeleteAccount() {
        BaseActions.click(By.xpath(HomePageLocators.HEADER_DELETE_ACCOUNT));
    }
}
