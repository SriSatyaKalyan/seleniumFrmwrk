package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.ContactPage;

public class aeContactUs {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    // TODO: SELF-HEALING LOCATORS
    public ContactPage contact = new ContactPage(getDriver());

    @When("User enters GetInTouch details {string}, {string}, {string} and {string}")
    public void userEntersContactDetails(String name, String email, String subject, String message) {
        contact.enterContactDetails(name, email, subject, message);
    }

    @Then("User validates submission of details")
    public void userValidatesSubmissionOfDetails() {
        contact.submitContactDetails();
    }
}
