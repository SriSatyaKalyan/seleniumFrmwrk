package stepDefinitions;

import interfaces.HomePageLocators;
import interfaces.URLs;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.HeaderSection;
import pages.LoginPage;
import pages.SignUpPage;
import pages.apiCalls;
import utils.Assertions;
import utils.BaseActions;
import utils.Logger;
import utils.WaitUtils;
import utils.Navigations;
import utils.JsonDataReader;

public class aeLogin {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    public LoginPage login = new LoginPage(getDriver());
    public HeaderSection header = new HeaderSection(getDriver());
    public SignUpPage signup = new SignUpPage(getDriver());

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
        signup.enterAccountInformation(name, emailAddress, password, day, month, year, Boolean.parseBoolean(wantNewsLetter));
    }

    @And("User fills in Address Information with {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string} and {string}")
    public void userFillsInAddressInformationWithDetails(String firstName, String lastName, String company, String address1, String address2, String state, String city, String zipcode, String mobileNumber) {
        signup.logAddressFormHeader();
        signup.enterAddressInformation(firstName, lastName, company, address1, address2, state, city, zipcode, mobileNumber);
    }

    @And("User clicks on Create Account button")
    public void userClicksOnCreateAccountButton() {
        signup.clickOnCreateAccount();
    }

    @Then("User verifies account creation")
    public void userVerifiesAccountCreation() {
        signup.verifyAccountCreation();
    }

    @And("User deletes account")
    public void userDeletesAccount() {
        header.clickDeleteAccount();
    }

    @Then("User verifies account deletion")
    public void userVerifiesAccountDeletion() {
        signup.verifyAccountDeletion();
    }

    @When("User enters credentials {string} and {string}")
    public void userEntersCredentials(String email, String password) {
        login.enterLoginDetails(email, password);
    }

    @Then("User lands on Home Page")
    public void userLandsOnHomePage(){
        WaitUtils.waitUntilVisible(getDriver(), By.xpath(HomePageLocators.HEADER_SECTION));
        Assertions.assertCurrentUrl(getDriver(), URLs.HOME_PAGE);
        header.validateLoggedInAsText();
    }

    @And("User clicks on Login button")
    public void userClicksOnLoginButton() {
        login.clickOnLogin();
    }

    @Then("User observes {string} message")
    public void userObservesErrorMessage(String errorMessage) {
        login.verifyErrorMessage(errorMessage);
    }

    @When("User clicks on Logout button")
    public void userClicksOnLogoutButton() {
        header.clickLogOutOption();
    }

    @Then("User is on SignUp-Login Page")
    public void userIsOnSignUpLoginPage() {
        login.verifyUserOnLoginPage();
    }

    @Then("User checks verifyLogin endpoint using {string} and {string}")
    public void userChecksVerifyLoginEndpointUsingCredentials(String email, String password) {
        apiCalls.verifyLoginEndpoint(email, password);
    }

    @Given("User creates account via API with {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void userCreatesAccountViaAPIWithDetails(String name, String email, String password, String title,
                                                   String birthDate, String birthMonth, String birthYear,
                                                   String firstname, String lastname, String company,
                                                   String address1, String address2, String country,
                                                   String state, String city, String zipcode, String mobileNumber) {
        apiCalls.createAccountEndpoint(name, email, password, title, birthDate, birthMonth, birthYear,
                firstname, lastname, company, address1, address2, country, state, city, zipcode, mobileNumber);
    }

    @When("User updates account via API with {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void userUpdatesAccountViaAPIWithNewDetails(String name, String email, String password, String title,
                                                       String birthDate, String birthMonth, String birthYear,
                                                       String firstname, String lastname, String company,
                                                       String address1, String address2, String country,
                                                       String state, String city, String zipcode, String mobileNumber) {
        apiCalls.updateAccountEndpoint(name, email, password, title, birthDate, birthMonth, birthYear,
                firstname, lastname, company, address1, address2, country, state, city, zipcode, mobileNumber);
    }

    @Then("User deletes account via API with {string}, {string}")
    public void userDeletesAccountViaAPIWithCredentials(String email, String password) {
        apiCalls.deleteAccountEndpoint(email, password);
    }

    @Given("User loads test data from JSON file {string}")
    public void userLoadsTestDataFromJSONFile(String fileName) {
        JsonDataReader.loadTestData(fileName);
    }

    @When("User creates account via API using loaded data")
    public void userCreatesAccountViaAPIUsingLoadedData() {
        apiCalls.createAccountEndpoint(
                JsonDataReader.getValue("name"),
                JsonDataReader.getValue("email"),
                JsonDataReader.getValue("password"),
                JsonDataReader.getValue("title"),
                JsonDataReader.getValue("birth_date"),
                JsonDataReader.getValue("birth_month"),
                JsonDataReader.getValue("birth_year"),
                JsonDataReader.getValue("firstname"),
                JsonDataReader.getValue("lastname"),
                JsonDataReader.getValue("company"),
                JsonDataReader.getValue("address1"),
                JsonDataReader.getValue("address2"),
                JsonDataReader.getValue("country"),
                JsonDataReader.getValue("state"),
                JsonDataReader.getValue("city"),
                JsonDataReader.getValue("zipcode"),
                JsonDataReader.getValue("mobile_number")
        );
    }

    @And("User updates account via API using loaded data")
    public void userUpdatesAccountViaAPIUsingLoadedData() {
        apiCalls.updateAccountEndpoint(
                JsonDataReader.getValue("name"),
                JsonDataReader.getValue("email"),
                JsonDataReader.getValue("password"),
                JsonDataReader.getValue("title"),
                JsonDataReader.getValue("birth_date"),
                JsonDataReader.getValue("birth_month"),
                JsonDataReader.getValue("birth_year"),
                JsonDataReader.getValue("firstname"),
                JsonDataReader.getValue("lastname"),
                JsonDataReader.getValue("updatedCompany"),
                JsonDataReader.getValue("address1"),
                JsonDataReader.getValue("address2"),
                JsonDataReader.getValue("country"),
                JsonDataReader.getValue("state"),
                JsonDataReader.getValue("city"),
                JsonDataReader.getValue("zipcode"),
                JsonDataReader.getValue("mobile_number")
        );
    }

    @Then("User deletes account via API using loaded data")
    public void userDeletesAccountViaAPIUsingLoadedData() {
        apiCalls.deleteAccountEndpoint(
                JsonDataReader.getValue("email"),
                JsonDataReader.getValue("password")
        );
    }
}
