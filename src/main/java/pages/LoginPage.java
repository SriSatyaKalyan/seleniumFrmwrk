package pages;

import interfaces.HomePageLocators;
import interfaces.LoginPageLocators;
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
        getSignupForm()
                .findElement(By.xpath(LoginPageLocators.SIGNUP_FORM_NAME))
                .sendKeys(name);

        getSignupForm()
                .findElement(By.xpath(LoginPageLocators.SIGNUP_FORM_EMAIL))
                .sendKeys(emailAddress);
    }

    public void enterLoginDetails(String email, String password) {
        BaseActions.enterIntoElement(BaseActions.find(By.xpath(LoginPageLocators.LOGIN_FORM_EMAIL)), email);
        BaseActions.enterIntoElement(BaseActions.find(By.xpath(LoginPageLocators.LOGIN_FORM_PASSWORD)), password);
    }

    public void clickOnLogin() {
        BaseActions.click(By.xpath(LoginPageLocators.LOGIN_BUTTON));
    }

    public void verifyAccountDeletion() {
        Assertions.assertCurrentUrl(getDriver(), URLs.DELETE_ACCOUNT_PAGE);
        BaseActions.printTextOfElement(By.xpath(LoginPageLocators.ACCOUNT_DELETED_HEADING));
        BaseActions.printTextOfElement(By.xpath(LoginPageLocators.ACCOUNT_DELETED_TEXT));
    }
}
