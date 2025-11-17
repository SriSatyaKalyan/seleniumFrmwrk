package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.FooterSection;

public class aeFooter {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    public FooterSection footer = new FooterSection(getDriver());

    @When("User observes Subscription option")
    public void userObservesSubscriptionOption() {
        footer.scrollToSubscriptionSection();
        footer.verifyEmailInputIsDisplayed();
    }

    @Then("User enters {string} in subscriptionForm")
    public void userEntersEmailIdInSubscriptionForm(String emailId) {
        footer.validateSubscriptionConfirmation(emailId);
    }

    @Then("User observes alert {string}")
    public void userObservesAlertMessage(String message) {
        footer.validateAlertMessage(message);
    }

    @Then("User observes validation {string}")
    public void userObservesValidationMessage(String message) {
        footer.validateFormFieldError(message);
    }
}
