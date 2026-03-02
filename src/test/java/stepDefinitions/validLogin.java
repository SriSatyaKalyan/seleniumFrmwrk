package stepDefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.ResetLoginPage;

public class validLogin {

    public ResetLoginPage resetLoginPage = new ResetLoginPage(getDriver());

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @When("User clicks on Sign In")
    public void userClicksOnSignIn() {
        resetLoginPage.clickSignIn();
    }

    @Then("User lands on Log In page with {string}")
    public void userLandsOnLogInPageWithWelcomeMessage(String welcomeMessage) {
        resetLoginPage.observeWelcomeMessage(welcomeMessage);
    }
}
