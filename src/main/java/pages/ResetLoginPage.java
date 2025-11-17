package pages;

import interfaces.ResetLoginPageLocators;
import interfaces.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Assertions;
import utils.BaseActions;
import utils.Logger;

public class ResetLoginPage extends BasePage {
    public ResetLoginPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
    }

    public void navigateToResetLoginPage() {
        getDriver().get(URLs.RESET_LOGIN_PAGE);
    }

    public void enterCredentials(String username, String password) {
        BaseActions.enterIntoElement(By.id(ResetLoginPageLocators.USERNAME_INPUT), username);
        BaseActions.enterIntoElement(By.xpath(ResetLoginPageLocators.PASSWORD_INPUT), password);
    }

    public void clickSignIn() {
        BaseActions.click(By.xpath(ResetLoginPageLocators.SIGN_IN_BUTTON));
    }

    public void observeErrorOnScreen() {
        WebElement errorElement = BaseActions.find(By.cssSelector(ResetLoginPageLocators.ERROR_MESSAGE));
        String errorText = errorElement.getText();
        Logger.info("Error message displayed: {}", errorText);
        Assertions.assertDisplayed(errorElement);
    }

    public void clickForgotPassword() {
        BaseActions.click(By.linkText(ResetLoginPageLocators.FORGOT_PASSWORD_LINK));
    }

    public void enterResetDetails(String name, String email, String phoneNumber) {
        BaseActions.enterIntoElement(By.xpath(ResetLoginPageLocators.NAME_INPUT), name);
        BaseActions.enterIntoElement(By.xpath(ResetLoginPageLocators.EMAIL_INPUT), email);
        BaseActions.enterIntoElement(By.cssSelector(ResetLoginPageLocators.PHONE_INPUT), phoneNumber);
    }

    public void clickResetLogin() {
        BaseActions.click(By.xpath(ResetLoginPageLocators.RESET_BUTTON));
    }

    public void observeTemporaryPasswordMessage() {
        WebElement passwordHelpElement = BaseActions.find(By.cssSelector(ResetLoginPageLocators.PASSWORD_HELP_TEXT));
        String passWordHelpText = passwordHelpElement.getText();
        Logger.info("The temporary password message: {}", passWordHelpText);
        Assertions.assertTextInElement(passwordHelpElement, "Please use temporary password 'rahulshettyacademy' to Login.");
    }

    public void observeWelcomeMessage(String welcomeMessage) {
        WebElement welcomeElement = BaseActions.find(By.xpath(ResetLoginPageLocators.WELCOME_MESSAGE(welcomeMessage)));
        Assertions.assertDisplayedWithMessage(welcomeElement, "Welcome message '" + welcomeMessage + "' not displayed");
        Logger.info("Login successful - current URL: {}", getDriver().getCurrentUrl());
    }
}