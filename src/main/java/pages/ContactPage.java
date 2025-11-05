package pages;

import interfaces.ContactPageLocators;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BaseActions;

public class ContactPage extends BasePage{

    public ContactPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
    }


    public void enterContactDetails(String name, String email, String subject, String message) {
        BaseActions.enterIntoElement(By.xpath(ContactPageLocators.CONTACT_NAME), name);
        BaseActions.enterIntoElement(By.xpath(ContactPageLocators.CONTACT_EMAIL), email);
        BaseActions.enterIntoElement(By.xpath(ContactPageLocators.CONTACT_SUBJECT), subject);
        BaseActions.enterIntoElement(By.xpath(ContactPageLocators.CONTACT_MESSAGE), message);
    }

    public void submitContactDetails() {
        BaseActions.click(By.xpath(ContactPageLocators.SUBMIT_BUTTON));
        BaseActions.handleAlert();
        BaseActions.printTextOfElement(By.xpath(ContactPageLocators.ALERT_SUCCESS));
    }
}
