package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class parabankLoginPage {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @Given("User is on Customer Login Page")
    public void user_on_customer_login_page(){
        getDriver().get("https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC");
    }

    @When("User click on Log In button")
    public void userClickOnLogInButton() {
        getDriver().findElement(By.xpath("//input[@value='Log In']")).click();
    }

    @Given("User provides creds {string} and {string}")
    public void userProvidesCredsUsernameAndPassword(String username, String password) {
        getDriver().findElement(By.xpath("//input[@name='username']")).sendKeys(username);
        getDriver().findElement(By.xpath("//input[@name='password']")).sendKeys(password);
    }

    @Then("User lands on Customer Home Page")
    public void userLandsOnCustomerHomePage() {

    }

    @Then("User observes {string} message")
    public void userObservesMessage(String error_message) {
        Assert.assertTrue(getDriver().findElement(By.xpath("//p[@class='error']")).getText().contains(error_message));
    }

    @Given("User clicks on Index button")
    public void userClicksOnIndexElement() {
        getDriver().findElement(By.xpath("//li[@class='home']")).click();
    }

    @Then("User lands on Index page")
    public void userLandsOnIndexPage() {
        System.out.println(getDriver().getTitle());
        Assert.assertTrue(getDriver().getTitle().contains("ParaBank | Welcome | Online Banking"));
    }

    @Given("User clicks on AboutUs button")
    public void userClicksOnAboutUsElement() {
        getDriver().findElement(By.xpath("//li[@class='aboutus']")).click();
    }

    @Then("User lands on AboutUs page")
    public void userLandsOnAboutUsPage() {
        System.out.println(getDriver().getTitle());
        Assert.assertTrue(getDriver().getTitle().contains("ParaBank | About Us"));
    }

    @Given("User clicks on Contact button")
    public void userClicksOnContactElement() {
        getDriver().findElement(By.xpath("//li[@class='contact']")).click();
    }

    @Then("User lands on Contact page")
    public void userLandsOnContactPage() {
        System.out.println(getDriver().getTitle());
        Assert.assertTrue(getDriver().getTitle().contains("ParaBank | Customer Care"));
    }
}