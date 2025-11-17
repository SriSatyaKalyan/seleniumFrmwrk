package pages;

import interfaces.ContactPageLocators;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseActions;
import utils.Logger;

public class ContactPage extends BasePage{

    public ContactPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
        Logger.info("ContactPage initialized successfully");
    }


    public void enterContactDetails(String name, String email, String subject, String message) {
        Logger.info("Entering contact details - Name: {}, Email: {}, Subject: {}", name, email, subject);
        BaseActions.enterIntoElement(By.xpath(ContactPageLocators.CONTACT_NAME), name);
        Logger.debug("Contact name entered: {}", name);
        BaseActions.enterIntoElement(By.xpath(ContactPageLocators.CONTACT_EMAIL), email);
        Logger.debug("Contact email entered: {}", email);
        BaseActions.enterIntoElement(By.xpath(ContactPageLocators.CONTACT_SUBJECT), subject);
        Logger.debug("Contact subject entered: {}", subject);
        BaseActions.enterIntoElement(By.xpath(ContactPageLocators.CONTACT_MESSAGE), message);
        Logger.debug("Contact message entered successfully");
        Logger.info("Contact details entered successfully");
    }

    public void submitContactDetails() {
        Logger.info("Submitting contact details");
        BaseActions.click(By.xpath(ContactPageLocators.SUBMIT_BUTTON));
        Logger.debug("Submit button clicked");
        BaseActions.handleAlert();
        Logger.debug("Alert handled successfully");
        BaseActions.printTextOfElement(By.xpath(ContactPageLocators.ALERT_SUCCESS));
        Logger.info("Contact details submitted successfully");
    }
}
