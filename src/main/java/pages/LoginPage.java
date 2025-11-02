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

    public void verifyAccountDeletion() {
        Assertions.assertCurrentUrl(getDriver(), URLs.DELETE_ACCOUNT_PAGE);
        BaseActions.printTextOfElement(By.xpath(LoginPageLocators.ACCOUNT_DELETED_HEADING));
        BaseActions.printTextOfElement(By.xpath(LoginPageLocators.ACCOUNT_DELETED_TEXT));
    }

    public void clickOnSignUp() {
        BaseActions.click(getSignupForm(), By.xpath(LoginPageLocators.SIGNUP_FORM_SUBMIT));
        BaseActions.printTextOfElement(By.xpath(SignUpPageLocators.SIGNUP_ACCOUNTINFO_TEXT));
    }

    public void enterAccountInformation(String name, String emailAddress, String password, String day, String month, String year, boolean wantNewsLetter) {
        BaseActions.click(By.xpath(SignUpPageLocators.SIGNUP_FORM_TITLE_Mr));
        Assertions.assertAttributeInElement(BaseActions.find(By.xpath(SignUpPageLocators.SIGNUP_FORM_NAME)), "value", name);
        Assertions.assertAttributeInElement(BaseActions.find(By.cssSelector(SignUpPageLocators.SIGNUP_FORM_EMAIL)), "value", emailAddress);
        Assertions.assertAttributeInElement(BaseActions.find(By.cssSelector(SignUpPageLocators.SIGNUP_FORM_EMAILADDRESS)), "type", "hidden");

        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_PASSWORD), password);
        BaseActions.scrollDown();

        enterBirthDetails(day, month, year);
        checkNewsLetterSelection(wantNewsLetter);
    }

    private void checkNewsLetterSelection(boolean wantNewsLetter) {
        Assertions.assertCheckBoxUnSelected(BaseActions.find(By.xpath(SignUpPageLocators.SIGNUP_FORM_NEWSLETTER)));
        if (wantNewsLetter) {
            BaseActions.click(By.xpath(SignUpPageLocators.SIGNUP_FORM_NEWSLETTER));
            Assertions.assertCheckBoxSelected(BaseActions.find(By.xpath(SignUpPageLocators.SIGNUP_FORM_NEWSLETTER)));
        }
    }

    private WebElement getDobDay() {
        return BaseActions.find(By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_DAY));
    }

    private WebElement getDobMonth() {
        return BaseActions.find(By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_MONTH));
    }

    private WebElement getDobYear() {
        return BaseActions.find(By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_YEAR));
    }

    private void enterBirthDetails(String day, String month, String year) {
        BaseActions.click(getDobDay(), By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_DAY_DROPDOWN));
        BaseActions.click(By.xpath(SignUpPageLocators.DAY_SELECTION(day)));

        BaseActions.click(getDobMonth(), By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_MONTH_DROPDOWN));
        BaseActions.click(By.xpath(SignUpPageLocators.MONTH_SELECTION(month)));

        BaseActions.click(getDobYear(), By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_YEAR_DROPDOWN));
        BaseActions.click(By.xpath(SignUpPageLocators.YEAR_SELECTION(year)));
    }

    public void enterAddressInformation(String firstName, String lastName, String company, String address1, String address2, String state, String city, String zipcode, String mobileNumber) {
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_FIRSTNAME), firstName);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_LASTNAME), lastName);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_COMPANY), company);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_ADDRESSI), address1);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_ADDRESSII), address2);

        BaseActions.scrollDown();

        BaseActions.click(By.xpath(SignUpPageLocators.SIGNUP_FORM_COUNTRY_DROPDOWN));
        BaseActions.click(By.xpath(SignUpPageLocators.SIGNUP_FORM_COUNTRY_US));

        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_STATE), state);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_CITY), city);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_ZIPCODE), zipcode);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_MOBILENUMBER), mobileNumber);
    }

    public void clickOnCreateAccount() {
        BaseActions.click(By.xpath(SignUpPageLocators.SIGNUP_FORM_CREATEACCOUNT));
    }

    public void verifyAccountCreation() {
        BaseActions.printTextOfElement(By.xpath(SignUpPageLocators.ACCOUNT_CREATED_HEADING));
        BaseActions.printTextOfElement(By.xpath(SignUpPageLocators.ACCOUNT_CREATED_TEXT));
        BaseActions.click(By.xpath(SignUpPageLocators.ACCOUNT_CREATION_CONTINUEBUTTON));
    }

    public void verifyErrorMessage(String message) {
        Assertions.assertTextInElement(BaseActions.find(By.xpath(LoginPageLocators.INVALID_LOGIN_ERROR_MSG)), message);
    }
}
