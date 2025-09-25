package pages;

import interfaces.PageActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

import java.time.Duration;

/**
 * Base page class containing common functionality for all pages
 * Demonstrates: Inheritance, Encapsulation, and Abstraction
 */
public abstract class BasePage implements PageActions {

    // Encapsulated WebDriver instance
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WaitUtils waitUtils;

    // Protected constructor - only child classes can access
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.waitUtils = new WaitUtils(driver);
        // Initialize page elements using PageFactory
        PageFactory.initElements(driver, this);
    }

    // Abstract method - forces child classes to implement
    public abstract boolean isPageLoaded();

    // Common implementation for all pages
    @Override
    public String getPageTitle() {
        return driver.getTitle();
    }

    @Override
    public void waitForPageLoad() {
        waitUtils.waitForPageToLoad();
    }

    // Protected methods available to child classes
    protected void clickElement(WebElement element) {
        waitUtils.waitForElementToBeClickable(element);
        element.click();
    }

    protected void enterText(WebElement element, String text) {
        waitUtils.waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getElementText(WebElement element) {
        waitUtils.waitForElementToBeVisible(element);
        return element.getText();
    }

    protected boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Protected getter for driver access in child classes
    protected WebDriver getDriver() {
        return driver;
    }
}