package pages;

import interfaces.HomePageLocators;
import interfaces.LoginPageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    public void enterRegistrationDetails(String name, String emailAddress) {
        getSignupForm()
                .findElement(By.xpath(LoginPageLocators.SIGNUP_FORM_NAME))
                .sendKeys(name);

        getSignupForm()
                .findElement(By.xpath(LoginPageLocators.SIGNUP_FORM_EMAIL))
                .sendKeys(emailAddress);
    }
}
