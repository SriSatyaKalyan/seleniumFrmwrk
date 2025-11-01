package stepDefinitions;

import interfaces.HomePageLocators;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FooterSection;
import pages.HeaderSection;
import utils.BaseActions;

import java.time.Duration;

public class aeFooter {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    public FooterSection footer = new FooterSection(getDriver());

    @When("User observes Subscription option")
    public void userObservesSubscriptionOption() {
        BaseActions.scrollIntoView(By.xpath(HomePageLocators.FOOTER_SUBSCRIPTION_SECTION));
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
        BaseActions.validateFormFieldError(By.id(HomePageLocators.EMAIL_INPUT), message);
    }
}
