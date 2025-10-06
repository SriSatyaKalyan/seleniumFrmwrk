package stepDefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class aeFooter {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    WebElement subscriptionSection;
    WebElement searchForm;
    WebElement emailInput;
    WebElement submitButton;

    @When("User observes Subscription option")
    public void userObservesSubscriptionOption() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        // Scroll to the footer/subscription section
        subscriptionSection = getDriver().findElement(By.xpath("//div[@class='single-widget']"));
        js.executeScript("arguments[0].scrollIntoView(true);", subscriptionSection);

        searchForm = subscriptionSection.findElement(By.xpath("//form[@class='searchform']"));
        emailInput = searchForm.findElement(By.id("susbscribe_email"));

        Assert.assertTrue(emailInput.isDisplayed());

    }

    @Then("User enters {string} in subscriptionForm")
    public void userEntersEmailIdInSubscriptionForm(String emailId) {
        emailInput.sendKeys(emailId);
        submitButton = searchForm.findElement(By.id("subscribe"));
        submitButton.click();
    }

    @Then("User observes alert {string}")
    public void userObservesAlertMessage(String message) {
        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='alert-success alert']")).isDisplayed());
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='alert-success alert']")).getText().toString(), message);
    }

    @Then("User observes validation {string}")
    public void userObservesValidationMessage(String message) {
        WebElement emailInp = getDriver().findElement(By.id("susbscribe_email"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String validationMessage = (String) js.executeScript(
                "return arguments[0].validationMessage;", emailInp);

        System.out.println(validationMessage);
        Assert.assertTrue(validationMessage != null && validationMessage.contains(message));
    }
}
