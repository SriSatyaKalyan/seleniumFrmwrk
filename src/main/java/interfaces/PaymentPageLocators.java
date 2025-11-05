package interfaces;

public interface PaymentPageLocators {

    String PAYMENT_FORM = "//form[@id='payment-form']";
    String PAYMENT_FORM_NAME = "//input[@name='name_on_card']";
    String PAYMENT_FORM_CARD_NUMBER = "//input[@name='card_number']";
    String PAYMENT_FORM_CVC = "//input[@class='form-control card-cvc']";
    String PAYMENT_FORM_CARD_EXPIRY_NUMBER = "//input[@class='form-control card-expiry-month']";
    String PAYMENT_FORM_CARD_EXPIRY_YEAR = "//input[@class='form-control card-expiry-year']";

    String PAY_BUTTON = "//button[@data-qa='pay-button']";
    String PAYMENT_SUCCESS_ALERT = "(//div[@class='alert-success alert'])[1]";

    Object PAYMENT_CONFIRMATION_TEXT = "payment_done";
    String ORDER_PLACEMENT_CONFIRMATION = "//*[@id='form']/div/div/div/p";
}
