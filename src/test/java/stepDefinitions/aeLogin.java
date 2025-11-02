package stepDefinitions;

import interfaces.HomePageLocators;
import interfaces.URLs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.HeaderSection;
import pages.LoginPage;
import utils.Assertions;
import utils.BaseActions;
import utils.Navigations;

public class aeLogin {

    JavascriptExecutor jse = (JavascriptExecutor) getDriver();

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    public LoginPage login = new LoginPage(getDriver());
    public HeaderSection header = new HeaderSection(getDriver());

    @Given("User is on AE Home Page")
    public void userIsOnAEHomePage() {
        Navigations.navigateTo(URLs.BASE_URL);
    }

    @Given("User is on AE Products Page")
    public void userIsOnAEProductsPage() {
        Navigations.navigateTo(URLs.PRODUCTS_PAGE);
    }

    @Given("User clicks on Login option")
    public void  userClicksOnLoginOption() {
        login.clickOnHeaderLogin();
    }

    @When("User enters signup details with {string} and {string}")
    public void userEntersSignUpDetails(String name, String emailAddress){
        login.enterSignUpDetails(name, emailAddress);
    }

    @Then("User clicks on SignUp button")
    public void userClicksOnSignUpButton() {
        login.clickOnSignUp();
    }

    @When("User fills in Account Information with {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void userFillsInAccountInformationWithDetails(String name, String emailAddress, String password, String day, String month, String year, String wantNewsLetter) {
        login.enterAccountInformation(name, emailAddress, password, day, month, year, Boolean.parseBoolean(wantNewsLetter));
    }

    @And("User fills in Address Information with {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and {string}")
    public void userFillsInAddressInformationWithDetails(String firstName, String lastName, String company, String address1, String address2, String state, String city, String zipcode, String mobileNumber) {
        System.out.println(getDriver().findElement(By.xpath("(//div[@class='login-form']//h2//b)[2]")).getText());
        login.enterAddressInformation(firstName, lastName, company, address1, address2, state, city, zipcode, mobileNumber);
    }

    @And("User clicks on Create Account button")
    public void userClicksOnCreateAccountButton() {
        login.clickOnCreateAccount();
    }

    @Then("User verifies account creation")
    public void userVerifiesAccountCreation() {
        login.verifyAccountCreation();
        // Get "Account Created!" text
//        String accountCreatedText = getDriver().findElement(By.xpath("//h2[@data-qa='account-created']")).getText();
//        System.out.println(accountCreatedText); // Output: ACCOUNT CREATED!
//
// Get the first paragraph text below it
//        String congratsText = getDriver().findElement(By.xpath("//h2[@data-qa='account-created']/following-sibling::p[1]")).getText();
//        System.out.println(congratsText); // Output: Congratulations! Your new account has been successfully created!
//
//        getDriver().findElement(By.xpath("//a[@data-qa='continue-button']")).click();
    }


    @And("User deletes account")
    public void userDeletesAccount() {
        header.clickDeleteAccount();
    }

    @Then("User verifies account deletion")
    public void userVerifiesAccountDeletion() {
        login.verifyAccountDeletion();
    }

    @When("User enters credentials {string} and {string}")
    public void userEntersCredentials(String email, String password) {
        login.enterLoginDetails(email, password);
    }

    @Then("User lands on Home Page")
    public void userLandsOnHomePage() throws InterruptedException {
        BaseActions.waitUntilVisible(By.xpath(HomePageLocators.HEADER_SECTION));
        Assertions.assertCurrentUrl(getDriver(), URLs.HOME_PAGE);
        header.validateLoggedInAsText();
    }

    @And("User clicks on Login button")
    public void userClicksOnLoginButton() {
        login.clickOnLogin();
    }

    @When("User enters invalid creds {string} and {string}")
    public void userEntersInvalidCredsEmailaddressAndPassword(String invalidEmailAddress, String invalidPassword) {
        getDriver().findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(invalidEmailAddress);
        getDriver().findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(invalidPassword);
    }

    @Then("User observes {string} message")
    public void userObservesErrorMessage(String errorMessage) {
        Assert.assertEquals(getDriver().findElement(By.xpath("//form[@action='/login']/child::p")).getText(), errorMessage);
    }

    @When("User clicks on Logout button")
    public void userClicksOnLogoutButton() {
        header.clickLogOutOption();
    }

    @Then("User is on SignUp-Login Page")
    public void userIsOnSignUpLoginPage() {
        Assertions.assertCurrentUrl(getDriver(), URLs.LOGIN_PAGE);
    }
}
