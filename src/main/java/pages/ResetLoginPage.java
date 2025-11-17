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
        Logger.info("ResetLoginPage initialized successfully");
    }

    public void navigateToResetLoginPage() {
        Logger.info("Navigating to Reset Login page: {}", URLs.RESET_LOGIN_PAGE);
        getDriver().get(URLs.RESET_LOGIN_PAGE);
        Logger.debug("Successfully navigated to Reset Login page");
    }

    public void enterCredentials(String username, String password) {
        Logger.info("Entering credentials for username: {}", username);
        BaseActions.enterIntoElement(By.id(ResetLoginPageLocators.USERNAME_INPUT), username);
        Logger.debug("Username entered: {}", username);
        BaseActions.enterIntoElement(By.xpath(ResetLoginPageLocators.PASSWORD_INPUT), password);
        Logger.debug("Password entered (masked for security)");
        Logger.info("Credentials entered successfully");
    }

    public void clickSignIn() {
        Logger.info("Clicking Sign In button");
        BaseActions.click(By.xpath(ResetLoginPageLocators.SIGN_IN_BUTTON));
        Logger.debug("Sign In button clicked successfully");
    }

    public void observeErrorOnScreen() {
        Logger.info("Checking for error message on screen");
        WebElement errorElement = BaseActions.find(By.cssSelector(ResetLoginPageLocators.ERROR_MESSAGE));
        String errorText = errorElement.getText();
        Logger.warn("Error message displayed: {}", errorText);
        Assertions.assertDisplayed(errorElement);
        Logger.debug("Error message validation completed");
    }

    public void clickForgotPassword() {
        Logger.info("Clicking Forgot Password link");
        BaseActions.click(By.linkText(ResetLoginPageLocators.FORGOT_PASSWORD_LINK));
        Logger.debug("Forgot Password link clicked successfully");
    }

    public void enterResetDetails(String name, String email, String phoneNumber) {
        Logger.info("Entering reset details - Name: {}, Email: {}, Phone: {}", name, email, phoneNumber);
        BaseActions.enterIntoElement(By.xpath(ResetLoginPageLocators.NAME_INPUT), name);
        Logger.debug("Name entered: {}", name);
        BaseActions.enterIntoElement(By.xpath(ResetLoginPageLocators.EMAIL_INPUT), email);
        Logger.debug("Email entered: {}", email);
        BaseActions.enterIntoElement(By.cssSelector(ResetLoginPageLocators.PHONE_INPUT), phoneNumber);
        Logger.debug("Phone number entered: {}", phoneNumber);
        Logger.info("Reset details entered successfully");
    }

    public void clickResetLogin() {
        Logger.info("Clicking Reset Login button");
        BaseActions.click(By.xpath(ResetLoginPageLocators.RESET_BUTTON));
        Logger.debug("Reset Login button clicked successfully");
    }

    public void observeTemporaryPasswordMessage() {
        Logger.info("Observing temporary password message");
        WebElement passwordHelpElement = BaseActions.find(By.cssSelector(ResetLoginPageLocators.PASSWORD_HELP_TEXT));
        String passWordHelpText = passwordHelpElement.getText();
        Logger.info("Temporary password message: {}", passWordHelpText);
        Assertions.assertTextInElement(passwordHelpElement, "Please use temporary password 'rahulshettyacademy' to Login.");
        Logger.debug("Temporary password message validation completed");
    }

    public void observeWelcomeMessage(String welcomeMessage) {
        Logger.info("Observing welcome message: {}", welcomeMessage);
        WebElement welcomeElement = BaseActions.find(By.xpath(ResetLoginPageLocators.WELCOME_MESSAGE(welcomeMessage)));
        Assertions.assertDisplayedWithMessage(welcomeElement, "Welcome message '" + welcomeMessage + "' not displayed");
        Logger.info("Login successful - current URL: {}", getDriver().getCurrentUrl());
        Logger.debug("Welcome message validation completed successfully");
    }
}