package pages;

import interfaces.HomePageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Assertions;
import utils.BaseActions;
import utils.Logger;

public class FooterSection extends BasePage {

    WebElement searchForm;
    WebElement emailInput;

    public FooterSection(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
        Logger.info("FooterSection initialized successfully");
    }

    public void verifyEmailInputIsDisplayed() {
        Logger.info("Verifying email input is displayed in footer");
        searchForm = BaseActions.find(getSubscriptionSection(), By.xpath(HomePageLocators.SUBSCRIPTION_SEARCH_FORM));
        Logger.debug("Subscription search form located");
        emailInput = BaseActions.find(searchForm, By.id(HomePageLocators.EMAIL_INPUT));
        Logger.debug("Email input field located");
        Assertions.assertDisplayed(emailInput);
        Logger.debug("Email input display verification completed successfully");
    }

    private WebElement getSubscriptionSection() {
        Logger.debug("Getting subscription section element from footer");
        WebElement subscriptionSection = BaseActions.find(By.xpath(HomePageLocators.FOOTER_SUBSCRIPTION_SECTION));
        Logger.debug("Subscription section element located successfully");
        return subscriptionSection;
    }

    public void validateSubscriptionConfirmation(String email) {
        Logger.info("Validating subscription confirmation for email: {}", email);
        BaseActions.enterIntoElement(searchForm, By.id(HomePageLocators.EMAIL_INPUT), email);
        Logger.debug("Email entered in subscription form: {}", email);
        BaseActions.click(searchForm, By.id(HomePageLocators.SUBSCRIBE_BUTTON));
        Logger.debug("Subscribe button clicked");
        Logger.info("Subscription confirmation process completed");
    }

    public void validateAlertMessage(String message) {
        Logger.info("Validating alert message: {}", message);
        Assertions.assertTextInElement(BaseActions.find(By.xpath(HomePageLocators.SUCCESS_ALERT)), message);
        Logger.debug("Alert message validation completed successfully");
    }

    public void scrollToSubscriptionSection() {
        Logger.info("Scrolling to subscription section in footer");
        BaseActions.scrollIntoView(By.xpath(HomePageLocators.FOOTER_SUBSCRIPTION_SECTION));
        Logger.debug("Successfully scrolled to subscription section");
    }

    public void validateFormFieldError(String message) {
        Logger.info("Validating form field error message: {}", message);
        BaseActions.validateFormFieldError(By.id(HomePageLocators.EMAIL_INPUT), message);
        Logger.debug("Form field error validation completed successfully");
    }
}