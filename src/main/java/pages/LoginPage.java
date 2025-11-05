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

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
    }

    public void clickOnHeaderLogin() {
        BaseActions.click(By.xpath(HomePageLocators.HEADER_SINGUP_LOGIN));
    }

    private WebElement getSignupForm() {
        return BaseActions.find(By.xpath(LoginPageLocators.SIGNUP_FORM));
    }

    public void enterSignUpDetails(String name, String emailAddress) {
        BaseActions.enterIntoElement(getSignupForm(), By.xpath(LoginPageLocators.SIGNUP_FORM_NAME), name);
        BaseActions.enterIntoElement(getSignupForm(), By.xpath(LoginPageLocators.SIGNUP_FORM_EMAIL), emailAddress);
    }

    public void enterLoginDetails(String email, String password) {
        BaseActions.enterIntoElement(By.xpath(LoginPageLocators.LOGIN_FORM_EMAIL), email);
        BaseActions.enterIntoElement(By.xpath(LoginPageLocators.LOGIN_FORM_PASSWORD), password);
    }

    public void clickOnLogin() {
        BaseActions.click(By.xpath(LoginPageLocators.LOGIN_BUTTON));
    }

    public void clickOnSignUp() {
        BaseActions.click(getSignupForm(), By.xpath(LoginPageLocators.SIGNUP_FORM_SUBMIT));
        BaseActions.printTextOfElement(By.xpath(SignUpPageLocators.SIGNUP_ACCOUNTINFO_TEXT));
    }

    public void verifyErrorMessage(String message) {
        Assertions.assertTextInElement(BaseActions.find(By.xpath(LoginPageLocators.INVALID_LOGIN_ERROR_MSG)), message);
    }

    public void verifyUserOnLoginPage() {
        Assertions.assertCurrentUrl(getDriver(), URLs.LOGIN_PAGE);
    }
}