package pages;

import interfaces.PaymentPageLocators;
import interfaces.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Assertions;
import utils.BaseActions;
import utils.Logger;
import utils.WaitUtils;

public class PaymentPage extends BasePage {

    public PaymentPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
        Logger.info("PaymentPage initialized successfully");
    }

    public void enterCardDetails(String name, String cardNumber, String cvc, String expiryNumber, String expiryYear) {
        Logger.info("Entering card details for payment - Name: {}, Expiry: {}/{}", name, expiryNumber, expiryYear);
        WebElement paymentForm = getDriver().findElement(By.xpath(PaymentPageLocators.PAYMENT_FORM));
        Logger.debug("Payment form located successfully");

        BaseActions.enterIntoElement(paymentForm, By.xpath(PaymentPageLocators.PAYMENT_FORM_NAME), name);
        Logger.debug("Name entered: {}", name);
        BaseActions.enterIntoElement(paymentForm, By.xpath(PaymentPageLocators.PAYMENT_FORM_CARD_NUMBER), cardNumber);
        Logger.debug("Card number entered (masked for security)");
        BaseActions.enterIntoElement(paymentForm, By.xpath(PaymentPageLocators.PAYMENT_FORM_CVC), cvc);
        Logger.debug("CVC entered (masked for security)");
        BaseActions.enterIntoElement(paymentForm, By.xpath(PaymentPageLocators.PAYMENT_FORM_CARD_EXPIRY_NUMBER), expiryNumber);
        Logger.debug("Expiry month entered: {}", expiryNumber);
        BaseActions.enterIntoElement(paymentForm, By.xpath(PaymentPageLocators.PAYMENT_FORM_CARD_EXPIRY_YEAR), expiryYear);
        Logger.debug("Expiry year entered: {}", expiryYear);
        Logger.info("Card details entered successfully");
    }

    public void placeOrder() {
        Logger.info("Placing order by clicking pay button");
        BaseActions.click(By.xpath(PaymentPageLocators.PAY_BUTTON));
        Logger.debug("Pay button clicked successfully");

        WebElement successAlert = BaseActions.find(By.xpath(PaymentPageLocators.PAYMENT_SUCCESS_ALERT));
        Logger.info("Payment success alert displayed: '{}'", successAlert.getText());
    }

    public void userConfirmsOrder() {
        Logger.info("Confirming order placement");
        WaitUtils.waitFluentlyUntilElementContains(getDriver(), PaymentPageLocators.PAYMENT_CONFIRMATION_TEXT.toString());
        Logger.debug("Payment confirmation text element found");

        Assertions.assertCurrentUrlContains(getDriver(), URLs.PAYMENT_DONE);
        Logger.debug("URL verification passed for payment completion");
        BaseActions.printTextOfElement(By.xpath(PaymentPageLocators.ORDER_PLACEMENT_CONFIRMATION));
        Logger.info("Order confirmation completed successfully");
    }
}
