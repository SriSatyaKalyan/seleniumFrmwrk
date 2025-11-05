package stepDefinitions;

import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.ContactPage;
import pages.ProductPage;

public class aeContactUs {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    // TODO: SELF-HEALING LOCATORS
    public ContactPage contact = new ContactPage(getDriver());

    @When("User enters GetInTouch details {string}, {string}, {string} and {string}")
    public void userEntersContactDetails(String name, String email, String subject, String message) {
        contact.enterContactDetails(name, email, subject, message);
        contact.submitContactDetails();
    }
}
