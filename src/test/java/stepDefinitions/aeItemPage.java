package stepDefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class aeItemPage {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @Then("User observes Review section")
    public void userObservesReviewSection() {
        WebElement reviewForm = getDriver().findElement(By.xpath("//form[@id='review-form']"));
        Assert.assertTrue(reviewForm.isDisplayed());
    }
}
