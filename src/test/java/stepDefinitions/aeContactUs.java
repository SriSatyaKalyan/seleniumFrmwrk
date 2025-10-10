package stepDefinitions;

import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class aeContactUs {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    // TODO: SELF-HEALING LOCATORS

    @When("User enters GetInTouch details {string}, {string}, {string} and {string}")
    public void userEntersGetInTouchDetailsNameEmailAndMessage(String name, String email, String subject, String message) {
        getDriver().findElement(By.xpath("//input[@data-qa='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//input[@data-qa='email']")).sendKeys(email);
        getDriver().findElement(By.xpath("//input[@data-qa='subject']")).sendKeys(subject);
        getDriver().findElement(By.xpath("//textarea[@data-qa='message']")).sendKeys(message);

        getDriver().findElement(By.xpath("//input[@data-qa='submit-button']")).click();

        Alert alert = getDriver().switchTo().alert();
        String alertText = alert.getText();
        System.out.println("Alert Text: " + alertText);

        alert.accept(); // Clicks "OK"

        System.out.println(getDriver().findElement(By.xpath("//div[@class='status alert alert-success']")).getText());
    }
}
