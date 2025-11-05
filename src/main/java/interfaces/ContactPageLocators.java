package interfaces;

import org.openqa.selenium.By;

public class ContactPageLocators {
    public static final String CONTACT_NAME = "//input[@data-qa='name']";
    public static final String CONTACT_EMAIL = "//input[@data-qa='email']";
    public static final String CONTACT_SUBJECT = "//input[@data-qa='subject']";
    public static final String CONTACT_MESSAGE = "//textarea[@data-qa='message']";


    public static final String SUBMIT_BUTTON = "//input[@data-qa='submit-button']";
    public static final String ALERT_SUCCESS = "//div[@class='status alert alert-success']";
}
