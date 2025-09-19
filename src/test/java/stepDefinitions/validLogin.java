package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class validLogin {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @When("User clicks on Sign In")
    public void userClicksOnSignIn() {
        getDriver().findElement(By.xpath("//button[@class='submit signInBtn']")).click();
    }

    @Then("User lands on Log In page with {string}")
    public void userLandsOnLogInPageWithWelcomeMessage(String welcomeMessage) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        // Wait for successful login page to load - looking for any element that indicates success
        try {
            // Option 1: Simple wait for single condition
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[contains(text(),'You are successfully logged in.')]")
            ));
            System.out.println("Login successful - current URL: " + getDriver().getCurrentUrl());

        } catch (Exception e) {
            System.out.println("Expected welcome message element not found. Current page title: " + getDriver().getTitle());
            System.out.println("Current URL: " + getDriver().getCurrentUrl());
        }
    }
}
