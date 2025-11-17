package interfaces;

public interface ResetLoginPageLocators {

    // LOGIN FORM
    String USERNAME_INPUT = "inputUsername";
    String PASSWORD_INPUT = "//input[@name='inputPassword']";
    String SIGN_IN_BUTTON = "//button[@class='submit signInBtn']";
    String ERROR_MESSAGE = "p.error";
    String FORGOT_PASSWORD_LINK = "Forgot your password?";

    // FORGOT PASSWORD FORM
    String NAME_INPUT = "//input[@placeholder='Name']";
    String EMAIL_INPUT = "//input[@type='text'][2]";
    String PHONE_INPUT = "input[type='text']:nth-child(4)";
    String RESET_BUTTON = "//button[@class='reset-pwd-btn']";
    String PASSWORD_HELP_TEXT = "form p";

    // WELCOME MESSAGE
    static String WELCOME_MESSAGE(String message) {
        return "//p[contains(text(),'" + message + "')]";
    }
}