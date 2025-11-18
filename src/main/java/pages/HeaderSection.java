package pages;

import interfaces.HomePageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.BaseActions;
import utils.Logger;
import utils.WaitUtils;

public class HeaderSection extends BasePage {
    public HeaderSection(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
        Logger.info("HeaderSection initialized successfully");
    }

    private WebElement getHeaderSection() {
        Logger.debug("Locating header section element");
        WebElement headerSection = BaseActions.find(By.xpath(HomePageLocators.HEADER_SECTION));
        Logger.debug("Header section element located successfully");
        return headerSection;
    }

    public void clickProductsOption() {
        Logger.info("Clicking on Products option in header");
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_PRODUCTS));
        Logger.debug("Products option clicked successfully");
    }

    public void clickCartOption() {
        Logger.info("Clicking on Cart option in header");
        WaitUtils.waitUntilClickable(getDriver(), By.xpath(HomePageLocators.HEADER_CART));
        Logger.debug("Cart option is clickable");
        BaseActions.click(By.xpath(HomePageLocators.HEADER_CART));
        Logger.debug("Cart option clicked successfully");
    }

    public void clickTestCasesOption() {
        Logger.info("Clicking on Test Cases option in header");
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_TESTCASES));
        Logger.debug("Test Cases option clicked successfully");
    }

    public void clickApiTestingOption() {
        Logger.info("Clicking on API Testing option in header");
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_APILIST));
        Logger.debug("API Testing option clicked successfully");
    }

    public void clickVideoTutorialsOption() {
        Logger.info("Clicking on Video Tutorials option in header");
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_VIDEOTUTORIALS));
        Logger.debug("Video Tutorials option clicked successfully");
    }

    public void clickContactUsOption() {
        Logger.info("Clicking on Contact Us option in header");
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_CONTACTUS));
        Logger.debug("Contact Us option clicked successfully");
    }

    public void clickLogOutOption() {
        Logger.info("Clicking on Logout option in header");
        BaseActions.click(getHeaderSection(), By.xpath(HomePageLocators.HEADER_LOGOUT));
        Logger.debug("Logout option clicked successfully");
    }

    public void validateLoggedInAsText() {
        Logger.info("Validating logged-in user text in header");
        BaseActions.printTextOfElement(By.xpath(HomePageLocators.HEADER_LOGGED_IN_TEXT));
        Logger.debug("Logged-in user text validation completed");
    }

    public void clickDeleteAccount() {
        Logger.info("Clicking on Delete Account option in header");
        BaseActions.click(By.xpath(HomePageLocators.HEADER_DELETE_ACCOUNT));
        Logger.debug("Delete Account option clicked successfully");
    }
}
