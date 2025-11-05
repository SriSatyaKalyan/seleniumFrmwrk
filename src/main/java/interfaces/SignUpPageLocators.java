package interfaces;

public interface SignUpPageLocators {
    String SIGNUP_ACCOUNTINFO_TEXT = "(//div[@class='login-form']//h2//b)[1]";

    String SIGNUP_FORM_TITLE_Mr = "//label[@for='id_gender1']";
    String SIGNUP_FORM_NAME = "//input[@id='name']";
    String SIGNUP_FORM_EMAIL = "input[class='form-control'][data-qa='email']";
    String SIGNUP_FORM_EMAILADDRESS = "input[name='email_address']";
    String SIGNUP_FORM_PASSWORD = "//input[@data-qa='password']";

    String SIGNUP_FORM_DOB_DAY = "#uniform-days";
    String SIGNUP_FORM_DOB_DAY_DROPDOWN = "#days";
    static String DAY_SELECTION(String day) {
        return "//select[@data-qa='days']//option[@value='" + day + "']";
    }

    String SIGNUP_FORM_DOB_MONTH = "#uniform-months";
    String SIGNUP_FORM_DOB_MONTH_DROPDOWN = "#months";
    static String MONTH_SELECTION(String month) {
        return "//select[@data-qa='months']//option[@value='" + month + "']";
    }

    String SIGNUP_FORM_DOB_YEAR = "#uniform-years";
    String SIGNUP_FORM_DOB_YEAR_DROPDOWN = "#years";
    static String YEAR_SELECTION(String year) {
        return "//select[@data-qa='years']//option[@value='" + year + "']";
    }

    String SIGNUP_FORM_NEWSLETTER = "//input[@name='newsletter']";

    String SIGNUP_FORM_FIRSTNAME = "//input[@id='first_name']";
    String SIGNUP_FORM_LASTNAME = "//input[@id='last_name']";
    String SIGNUP_FORM_COMPANY = "//input[@id='company']";
    String SIGNUP_FORM_ADDRESSI = "//input[@id='address1']";
    String SIGNUP_FORM_ADDRESSII = "//input[@id='address2']";

    String SIGNUP_FORM_COUNTRY_DROPDOWN = "//select[@id='country']";
    String SIGNUP_FORM_COUNTRY_US = "//option[@value='United States']";
    String SIGNUP_FORM_STATE = "//input[@id='state']";
    String SIGNUP_FORM_CITY = "//input[@id='city']";
    String SIGNUP_FORM_ZIPCODE = "//input[@id='zipcode']";
    String SIGNUP_FORM_MOBILENUMBER = "//input[@id='mobile_number']";

    String SIGNUP_FORM_CREATEACCOUNT = "(//button[@type='submit'])[1]";

    String ACCOUNT_CREATED_HEADING = "//h2[@data-qa='account-created']";
    String ACCOUNT_CREATED_TEXT = "//h2[@data-qa='account-created']/following-sibling::p[1]";
    String ACCOUNT_CREATION_CONTINUEBUTTON = "//a[@data-qa='continue-button']";
}