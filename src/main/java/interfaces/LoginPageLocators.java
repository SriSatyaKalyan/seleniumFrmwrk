package interfaces;

public interface LoginPageLocators {
    String SIGNUP_FORM = "//div[@class='signup-form']";
    String SIGNUP_FORM_NAME = "//input[@data-qa='signup-name']";
    String SIGNUP_FORM_EMAIL = "//input[@data-qa='signup-email']";
    String SIGNUP_FORM_SUBMIT = "//button[@data-qa='signup-button']";

    String LOGIN_FORM_EMAIL = "//input[@data-qa='login-email']";
    String LOGIN_FORM_PASSWORD = "//input[@data-qa='login-password']";
    String LOGIN_BUTTON = "//button[@data-qa='login-button']";

    //ACCOUNT DELETION
    String ACCOUNT_DELETED_HEADING = "//h2[@data-qa='account-deleted']";
    String ACCOUNT_DELETED_TEXT = "//h2[@data-qa='account-deleted']/following-sibling::p[1]";

    String INVALID_LOGIN_ERROR_MSG = "//form[@action='/login']/child::p";
}