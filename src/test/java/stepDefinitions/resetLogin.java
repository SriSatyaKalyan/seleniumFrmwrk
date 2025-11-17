package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.ResetLoginPage;

public class resetLogin {

    public ResetLoginPage resetLoginPage = new ResetLoginPage(getDriver());

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @Given("User is on Login Page")
    public void userOnLoginPage() {
        resetLoginPage.navigateToResetLoginPage();
    }

    @Given("User logs in using {string} and {string}")
    public void user_logs_in(String username, String password) {
        resetLoginPage.enterCredentials(username, password);
        resetLoginPage.clickSignIn();
    }

    @Then("User observes error on screen")
    public void userObservesErrorOnScreen() {
        resetLoginPage.observeErrorOnScreen();
    }

    @When("User clicks on Forgot Password link")
    public void userClicksForgotPassword() {
        resetLoginPage.clickForgotPassword();
    }

    @When("User provides the details for {string}, {string}, {string}")
    public void userProvidesDetails(String name, String email, String phoneNumber) {
        resetLoginPage.enterResetDetails(name, email, phoneNumber);
    }

    @When("User clicks on Reset Login button")
    public void userClicksResetPassword() {
        resetLoginPage.clickResetLogin();
    }

    @Then("User observes temporary password message")
    public void userObservesTemporaryPasswordMessage() {
        resetLoginPage.observeTemporaryPasswordMessage();
    }
}
