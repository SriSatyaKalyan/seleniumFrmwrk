package pages;

import interfaces.LoginPageLocators;
import interfaces.SignUpPageLocators;
import interfaces.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Assertions;
import utils.BaseActions;
import utils.Logger;

public class SignUpPage extends BasePage {

    public SignUpPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
        Logger.info("SignUpPage initialized successfully");
    }

    public void enterAccountInformation(String name, String emailAddress, String password, String day, String month, String year, boolean wantNewsLetter) {
        Logger.info("Entering account information - Name: {}, Email: {}, DOB: {}/{}/{}, Newsletter: {}", name, emailAddress, day, month, year, wantNewsLetter);
        BaseActions.click(By.xpath(SignUpPageLocators.SIGNUP_FORM_TITLE_Mr));
        Logger.debug("Title 'Mr' selected");
        Assertions.assertAttributeInElement(BaseActions.find(By.xpath(SignUpPageLocators.SIGNUP_FORM_NAME)), "value", name);
        Logger.debug("Name field validation passed");
        Assertions.assertAttributeInElement(BaseActions.find(By.cssSelector(SignUpPageLocators.SIGNUP_FORM_EMAIL)), "value", emailAddress);
        Logger.debug("Email field validation passed");
        Assertions.assertAttributeInElement(BaseActions.find(By.cssSelector(SignUpPageLocators.SIGNUP_FORM_EMAILADDRESS)), "type", "hidden");
        Logger.debug("Hidden email field validation passed");

        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_PASSWORD), password);
        Logger.debug("Password entered (masked for security)");
        BaseActions.scrollDown();
        Logger.debug("Page scrolled down");

        enterBirthDetails(day, month, year);
        checkNewsLetterSelection(wantNewsLetter);
        Logger.info("Account information entered successfully");
    }

    private void enterBirthDetails(String day, String month, String year) {
        Logger.debug("Entering birth details - Day: {}, Month: {}, Year: {}", day, month, year);
        BaseActions.click(getDobDay(), By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_DAY_DROPDOWN));
        BaseActions.click(By.xpath(SignUpPageLocators.DAY_SELECTION(day)));
        Logger.debug("Day selected: {}", day);

        BaseActions.click(getDobMonth(), By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_MONTH_DROPDOWN));
        BaseActions.click(By.xpath(SignUpPageLocators.MONTH_SELECTION(month)));
        Logger.debug("Month selected: {}", month);

        BaseActions.click(getDobYear(), By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_YEAR_DROPDOWN));
        BaseActions.click(By.xpath(SignUpPageLocators.YEAR_SELECTION(year)));
        Logger.debug("Year selected: {}", year);
        Logger.debug("Birth details entered successfully");
    }

    private WebElement getDobDay() {
        Logger.debug("Getting DOB day element");
        return BaseActions.find(By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_DAY));
    }

    private WebElement getDobMonth() {
        Logger.debug("Getting DOB month element");
        return BaseActions.find(By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_MONTH));
    }

    private WebElement getDobYear() {
        Logger.debug("Getting DOB year element");
        return BaseActions.find(By.cssSelector(SignUpPageLocators.SIGNUP_FORM_DOB_YEAR));
    }

    private void checkNewsLetterSelection(boolean wantNewsLetter) {
        Logger.debug("Checking newsletter selection. Want newsletter: {}", wantNewsLetter);
        Assertions.assertCheckBoxUnSelected(BaseActions.find(By.xpath(SignUpPageLocators.SIGNUP_FORM_NEWSLETTER)));
        Logger.debug("Newsletter checkbox initially unselected - validation passed");
        if (wantNewsLetter) {
            BaseActions.click(By.xpath(SignUpPageLocators.SIGNUP_FORM_NEWSLETTER));
            Logger.debug("Newsletter checkbox clicked");
            Assertions.assertCheckBoxSelected(BaseActions.find(By.xpath(SignUpPageLocators.SIGNUP_FORM_NEWSLETTER)));
            Logger.debug("Newsletter checkbox selected - validation passed");
        }
        Logger.debug("Newsletter selection completed");
    }

    public void logAddressFormHeader() {
        WebElement addressFormHeader = BaseActions.find(By.xpath(SignUpPageLocators.SIGNUP_ADDRESSINFO_TEXT));
        Logger.info("Form header: {}", addressFormHeader.getText());
    }

    public void enterAddressInformation(String firstName, String lastName, String company, String address1, String address2, String state, String city, String zipcode, String mobileNumber) {
        Logger.info("Entering address information - Name: {} {}, City: {}, State: {}, Zipcode: {}", firstName, lastName, city, state, zipcode);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_FIRSTNAME), firstName);
        Logger.debug("First name entered: {}", firstName);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_LASTNAME), lastName);
        Logger.debug("Last name entered: {}", lastName);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_COMPANY), company);
        Logger.debug("Company entered: {}", company);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_ADDRESSI), address1);
        Logger.debug("Address 1 entered: {}", address1);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_ADDRESSII), address2);
        Logger.debug("Address 2 entered: {}", address2);

        BaseActions.scrollDown();
        Logger.debug("Page scrolled down for additional fields");

        BaseActions.click(By.xpath(SignUpPageLocators.SIGNUP_FORM_COUNTRY_DROPDOWN));
        BaseActions.click(By.xpath(SignUpPageLocators.SIGNUP_FORM_COUNTRY_US));
        Logger.debug("Country selected: United States");

        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_STATE), state);
        Logger.debug("State entered: {}", state);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_CITY), city);
        Logger.debug("City entered: {}", city);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_ZIPCODE), zipcode);
        Logger.debug("Zipcode entered: {}", zipcode);
        BaseActions.enterIntoElement(By.xpath(SignUpPageLocators.SIGNUP_FORM_MOBILENUMBER), mobileNumber);
        Logger.debug("Mobile number entered: {}", mobileNumber);
        Logger.info("Address information entered successfully");
    }

    public void clickOnCreateAccount() {
        Logger.info("Clicking on Create Account button");
        BaseActions.click(By.xpath(SignUpPageLocators.SIGNUP_FORM_CREATEACCOUNT));
        Logger.debug("Create Account button clicked successfully");
    }

    public void verifyAccountCreation() {
        Logger.info("Verifying account creation");
        BaseActions.printTextOfElement(By.xpath(SignUpPageLocators.ACCOUNT_CREATED_HEADING));
        Logger.debug("Account creation heading displayed");
        BaseActions.printTextOfElement(By.xpath(SignUpPageLocators.ACCOUNT_CREATED_TEXT));
        Logger.debug("Account creation text displayed");
        BaseActions.click(By.xpath(SignUpPageLocators.ACCOUNT_CREATION_CONTINUEBUTTON));
        Logger.debug("Continue button clicked");
        Logger.info("Account creation verification completed successfully");
    }

    public void verifyAccountDeletion() {
        Logger.info("Verifying account deletion");
        Assertions.assertCurrentUrl(getDriver(), URLs.DELETE_ACCOUNT_PAGE);
        Logger.debug("Delete account page URL validation passed");
        BaseActions.printTextOfElement(By.xpath(LoginPageLocators.ACCOUNT_DELETED_HEADING));
        Logger.debug("Account deletion heading displayed");
        BaseActions.printTextOfElement(By.xpath(LoginPageLocators.ACCOUNT_DELETED_TEXT));
        Logger.debug("Account deletion text displayed");
        Logger.info("Account deletion verification completed successfully");
    }
}
