package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class resetLogin {

    private static WebDriver driver;

    @Given("User is on Login Page")
    public void user_is_on_login_page() {
        System.setProperty("webdriver.chrome.driver", "/Users/srisatyakalyankallepalli/Documents/GitHub/seleniumFrmwrk/chromedriver");
        // Create a new instance of the Chrome browser driver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://rahulshettyacademy.com/locatorspractice/");
    }

    @Given("User logs in using {string} and {string}")
    public void user_logs_in(String username, String password) {
        // Fill in the username and password fields
        driver.findElement(By.id("inputUsername")).sendKeys(username);
        driver.findElement(By.xpath("//input[@name='inputPassword']")).sendKeys(password);

        // Click the Sign In button
        driver.findElement(By.xpath("//button[@class='submit signInBtn']")).click();

        // Print the error text
        String errorText = driver.findElement(By.cssSelector("p.error")).getText();
        System.out.println(errorText);
    }

    @When("User clicks on Forgot Password link")
    public void user_clicks_forgotPassword() {
        // Click the Forgot Your Password link
        driver.findElement(By.linkText("Forgot your password?")).click();
    }

    @When("User provides the details for {string}, {string}, {string}")
    public void user_provides_details(String name, String email, String phoneNumber) {
        // Fill in the Forgot Password form
        driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys(name);
        driver.findElement(By.xpath("//input[@type='text'][2]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[type='text']:nth-child(4)")).sendKeys(phoneNumber);
    }

    @When("User clicks on Reset Login button")
    public void user_clicks_ResetPassword(){
        // Submit the Reset Login form
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='reset-pwd-btn']")));
        driver.findElement(By.xpath("//button[@class='reset-pwd-btn']")).click();

        // Print the password help text
        String passWordHelpText = driver.findElement(By.cssSelector("form p")).getText();
        System.out.println("The temporary password message: " + passWordHelpText);
        // System.out.println("The temporary password message: " + tempPasswordMessage);
        Assert.assertEquals(passWordHelpText, "Please use temporary password 'rahulshettyacademy' to Login.");
    }

    @Then("User observes {string} message")
    public void userShouldSeeTempPasswordMessageMessage(String tempPasswordMessage) {
        String passWordHelpText = driver.findElement(By.cssSelector("form p")).getText();
        System.out.println("The temporary password message: " + passWordHelpText);
        Assert.assertEquals(passWordHelpText, tempPasswordMessage);
    }

    @Then("User quits the browser")
    public void userQuitsTheBrowser() {
        // Close the browser and end the WebDriver session
        driver.quit();
    }

//    @Then("User receives {string} message")
//    public void userReceivesTempPasswordMessageMessage() {
//    }
}
