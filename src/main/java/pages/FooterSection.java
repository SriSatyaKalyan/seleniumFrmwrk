package pages;

import interfaces.HomePageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Assertions;
import utils.BaseActions;

public class FooterSection extends BasePage {

    WebElement searchForm;
    WebElement emailInput;

    public FooterSection(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
    }

    public void verifyEmailInputIsDisplayed() {
        searchForm = BaseActions.find(getSubscriptionSection(), By.xpath(HomePageLocators.SUBSCRIPTION_SEARCH_FORM));
        emailInput = BaseActions.find(searchForm, By.id(HomePageLocators.EMAIL_INPUT));
        Assertions.assertDisplayed(emailInput);
    }

    private WebElement getSubscriptionSection() {
        return BaseActions.find(By.xpath(HomePageLocators.FOOTER_SUBSCRIPTION_SECTION));
    }

    public void validateSubscriptionConfirmation(String email) {
        BaseActions.enterIntoElement(searchForm, By.id(HomePageLocators.EMAIL_INPUT), email);
        BaseActions.click(searchForm, By.id(HomePageLocators.SUBSCRIBE_BUTTON));
    }

    public void validateAlertMessage(String message) {
        Assertions.assertTextInElement(BaseActions.find(By.xpath(HomePageLocators.SUCCESS_ALERT)), message);
    }

    public void scrollToSubscriptionSection() {
        BaseActions.scrollIntoView(By.xpath(HomePageLocators.FOOTER_SUBSCRIPTION_SECTION));
    }

    public void validateFormFieldError(String message) {
        BaseActions.validateFormFieldError(By.id(HomePageLocators.EMAIL_INPUT), message);
    }
}