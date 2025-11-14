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

import java.util.Objects;

public class PaymentPage extends BasePage {

    public PaymentPage(WebDriver driver) {
        super(driver);
        BaseActions.setDriver(driver);
    }

    public void enterCardDetails(String name, String cardNumber, String cvc, String expiryNumber, String expiryYear) {
        WebElement paymentForm = getDriver().findElement(By.xpath(PaymentPageLocators.PAYMENT_FORM));

        BaseActions.enterIntoElement(paymentForm, By.xpath(PaymentPageLocators.PAYMENT_FORM_NAME), name);
        BaseActions.enterIntoElement(paymentForm, By.xpath(PaymentPageLocators.PAYMENT_FORM_CARD_NUMBER), cardNumber);
        BaseActions.enterIntoElement(paymentForm, By.xpath(PaymentPageLocators.PAYMENT_FORM_CVC), cvc);
        BaseActions.enterIntoElement(paymentForm, By.xpath(PaymentPageLocators.PAYMENT_FORM_CARD_EXPIRY_NUMBER), expiryNumber);
        BaseActions.enterIntoElement(paymentForm, By.xpath(PaymentPageLocators.PAYMENT_FORM_CARD_EXPIRY_YEAR), expiryYear);
    }

    public void placeOrder() {
        BaseActions.click(By.xpath(PaymentPageLocators.PAY_BUTTON));

        WebElement successAlert = BaseActions.find(By.xpath(PaymentPageLocators.PAYMENT_SUCCESS_ALERT));
        Logger.info("The success alert is: '{}'", successAlert.getText());
    }

    public void userConfirmsOrder() {
        WaitUtils.waitFluentlyUntilElementContains(getDriver(), PaymentPageLocators.PAYMENT_CONFIRMATION_TEXT.toString());

        Assertions.assertCurrentUrlContains(getDriver(), URLs.PAYMENT_DONE);
        BaseActions.printTextOfElement(By.xpath(PaymentPageLocators.ORDER_PLACEMENT_CONFIRMATION));
    }
}
