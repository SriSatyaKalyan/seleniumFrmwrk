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

public class resetLogin {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @Given("User is on Login Page")
    public void user_is_on_login_page() {
        // WebDriver is already initialized by testBase @Before method
        getDriver().get("https://rahulshettyacademy.com/locatorspractice/");
    }

    @Given("User logs in using {string} and {string}")
    public void user_logs_in(String username, String password) {
        // Wait for and fill in the username and password fields
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));

        getDriver().findElement(By.id("inputUsername")).sendKeys(username);
        getDriver().findElement(By.xpath("//input[@name='inputPassword']")).sendKeys(password);

        // Click the Sign In button
        getDriver().findElement(By.xpath("//button[@class='submit signInBtn']")).click();
    }

    @Then("User observes error on screen")
    public void userObservesErrorOnScreen() {
        // Wait for error element and print the error text
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.error")));
        String errorText = getDriver().findElement(By.cssSelector("p.error")).getText();
        System.out.println(errorText);
    }

    @When("User clicks on Forgot Password link")
    public void user_clicks_forgotPassword() {
        // Click the Forgot Your Password link
        getDriver().findElement(By.linkText("Forgot your password?")).click();
    }

    @When("User provides the details for {string}, {string}, {string}")
    public void user_provides_details(String name, String email, String phoneNumber) {
        // Wait for and fill in the Forgot Password form
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']")));

        getDriver().findElement(By.xpath("//input[@placeholder='Name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//input[@type='text'][2]")).sendKeys(email);
        getDriver().findElement(By.cssSelector("input[type='text']:nth-child(4)")).sendKeys(phoneNumber);
    }

    @When("User clicks on Reset Login button")
    public void user_clicks_ResetPassword() {
        // Submit the Reset Login form
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='reset-pwd-btn']")));
        getDriver().findElement(By.xpath("//button[@class='reset-pwd-btn']")).click();
    }

    @Then("User observes temporary password message")
    public void user_observes_temporary_password_message() {
        // Print the password help text
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form p")));
        String passWordHelpText = getDriver().findElement(By.cssSelector("form p")).getText();
        System.out.println("The temporary password message: " + passWordHelpText);
        Assert.assertEquals(passWordHelpText, "Please use temporary password 'rahulshettyacademy' to Login.");
    }
}
