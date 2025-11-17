package pages;

import interfaces.HomePageLocators;
import interfaces.LoginPageLocators;
import interfaces.SignUpPageLocators;
import interfaces.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Assertions;
import utils.BaseActions;
import utils.Logger;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
        Logger.info("LoginPage initialized successfully");
    }

    public void clickOnHeaderLogin() {
        Logger.info("Clicking on header login/signup link");
        BaseActions.click(By.xpath(HomePageLocators.HEADER_SINGUP_LOGIN));
        Logger.debug("Header login/signup link clicked successfully");
    }

    private WebElement getSignupForm() {
        Logger.debug("Locating signup form element");
        WebElement signupForm = BaseActions.find(By.xpath(LoginPageLocators.SIGNUP_FORM));
        Logger.debug("Signup form element located successfully");
        return signupForm;
    }

    public void enterSignUpDetails(String name, String emailAddress) {
        Logger.info("Entering signup details - Name: {}, Email: {}", name, emailAddress);
        BaseActions.enterIntoElement(getSignupForm(), By.xpath(LoginPageLocators.SIGNUP_FORM_NAME), name);
        Logger.debug("Name entered successfully: {}", name);
        BaseActions.enterIntoElement(getSignupForm(), By.xpath(LoginPageLocators.SIGNUP_FORM_EMAIL), emailAddress);
        Logger.debug("Email entered successfully: {}", emailAddress);
    }

    public void enterLoginDetails(String email, String password) {
        Logger.info("Entering login details for email: {}", email);
        BaseActions.enterIntoElement(By.xpath(LoginPageLocators.LOGIN_FORM_EMAIL), email);
        Logger.debug("Email entered successfully: {}", email);
        BaseActions.enterIntoElement(By.xpath(LoginPageLocators.LOGIN_FORM_PASSWORD), password);
        Logger.debug("Password entered successfully (hidden for security)");
    }

    public void clickOnLogin() {
        Logger.info("Clicking on login button");
        BaseActions.click(By.xpath(LoginPageLocators.LOGIN_BUTTON));
        Logger.debug("Login button clicked successfully");
    }

    public void clickOnSignUp() {
        Logger.info("Clicking on signup button");
        BaseActions.click(getSignupForm(), By.xpath(LoginPageLocators.SIGNUP_FORM_SUBMIT));
        Logger.debug("Signup button clicked successfully");
        BaseActions.printTextOfElement(By.xpath(SignUpPageLocators.SIGNUP_ACCOUNTINFO_TEXT));
        Logger.debug("Signup account info text displayed");
    }

    public void verifyErrorMessage(String message) {
        Logger.info("Verifying error message: {}", message);
        Assertions.assertTextInElement(BaseActions.find(By.xpath(LoginPageLocators.INVALID_LOGIN_ERROR_MSG)), message);
        Logger.debug("Error message verification completed successfully");
    }

    public void verifyUserOnLoginPage() {
        Logger.info("Verifying user is on login page");
        Assertions.assertCurrentUrl(getDriver(), URLs.LOGIN_PAGE);
        Logger.debug("Login page URL verification completed successfully");
    }
}