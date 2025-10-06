package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class aeLogin {

    JavascriptExecutor jse = (JavascriptExecutor) getDriver();

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @Given("User is on AE Home Page")
    public void userIsOnAEHomePage() {
        getDriver().get("https://www.automationexercise.com/");
    }

    @Given("User clicks on Login option")
    public void userClicksOnLoginOption() {
        getDriver().findElement(By.xpath("//a[@href='/login']")).click();
        System.out.println(getDriver().getTitle());
    }

    @When("User enters registration details")
    public void userEntersRegistrationDetails() throws InterruptedException {
        //parent-child traversal
        WebElement signupForm = getDriver().findElement(By.xpath("//div[@class='signup-form']"));
        System.out.println(signupForm.findElement(By.tagName("h2")).getText());
        signupForm.findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys("John Dough");
        signupForm.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys("jdough@gmail.com");
//        signupForm.findElement(By.xpath("//button[@data-qa='signup-button']")).click();
//        System.out.println(getDriver().findElement(By.xpath("(//div[@class='login-form']//h2//b)[1]")).getText());
    }

    @Then("User clicks on SignUp button")
    public void userClicksOnSignUpButton() {
        WebElement signupForm = getDriver().findElement(By.xpath("//div[@class='signup-form']"));
        signupForm.findElement(By.xpath("//button[@data-qa='signup-button']")).click();
        System.out.println(getDriver().findElement(By.xpath("(//div[@class='login-form']//h2//b)[1]")).getText());
    }

    @When("User fills in Account Information")
    public void userFillsInAccountInformation() {
        getDriver().findElement(By.xpath("//label[@for='id_gender1']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@id='name']")).getAttribute("value"), "John Dough");

        Assert.assertEquals(getDriver().findElement(By.cssSelector("input[class='form-control'][data-qa='email']")).getAttribute("value"), "jdough@gmail.com");
        Assert.assertEquals(getDriver().findElement(By.cssSelector("input[name='email_address']")).getAttribute("type"), "hidden");

        getDriver().findElement(By.xpath("//input[@data-qa='password']")).sendKeys("j%hnD*ug!");

        WebElement dobDay = getDriver().findElement(By.cssSelector("#uniform-days"));

        jse.executeScript("window.scrollBy(0, 500);");

        dobDay.findElement(By.cssSelector("#days")).click();
        dobDay.findElement(By.xpath("//select[@data-qa='days']//option[@value='15']")).click();

        WebElement dobMonth = getDriver().findElement(By.cssSelector("#uniform-months"));
        dobMonth.findElement(By.cssSelector("#months")).click();
        dobMonth.findElement(By.xpath("//option[@value='8']")).click();

        WebElement dobYear = getDriver().findElement(By.cssSelector("#uniform-years"));
        dobYear.findElement(By.cssSelector("#years")).click();
        dobYear.findElement(By.xpath("//option[@value='2000']")).click();

        WebElement newsletterCheckbox = getDriver().findElement(By.xpath("//input[@name='newsletter']"));
        Assert.assertFalse(newsletterCheckbox.isSelected());
        newsletterCheckbox.click();
        Assert.assertTrue(newsletterCheckbox.isSelected());

        if(newsletterCheckbox.isSelected()){
            System.out.println("The checkbox has been selected");
        }else {
            System.out.println("The checkbox has NOT been selected");
        }
    }

    @And("User fills in Address Information")
    public void userFillsInAddressInformation() {
        System.out.println(getDriver().findElement(By.xpath("(//div[@class='login-form']//h2//b)[2]")).getText());

        getDriver().findElement(By.xpath("//input[@id='first_name']")).sendKeys("John");
        getDriver().findElement(By.xpath("//input[@id='last_name']")).sendKeys("Dough");
        getDriver().findElement(By.xpath("//input[@id='company']")).sendKeys("Dough Imports & Exports");
        getDriver().findElement(By.xpath("//input[@id='address1']")).sendKeys("123 Happy Lane");
        getDriver().findElement(By.xpath("//input[@id='address2']")).sendKeys("Suite No: 456");

        jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.xpath("//select[@id='country']")).click();
        getDriver().findElement(By.xpath("//option[@value='United States']")).click();

        getDriver().findElement(By.xpath("//input[@id='state']")).sendKeys("California");
        getDriver().findElement(By.xpath("//input[@id='city']")).sendKeys("Westwood");
        getDriver().findElement(By.xpath("//input[@id='zipcode']")).sendKeys("90009");
        getDriver().findElement(By.xpath("//input[@id='mobile_number']")).sendKeys("9798998888");
    }

    @And("User clicks on Create Account button")
    public void userClicksOnCreateAccountButton() {
        getDriver().findElement(By.xpath("(//button[@type='submit'])[1]")).click();
    }

    @Then("User verifies account creation")
    public void userVerifiesAccountCreation() {
        // Get "Account Created!" text
        String accountCreatedText = getDriver().findElement(By.xpath("//h2[@data-qa='account-created']")).getText();
        System.out.println(accountCreatedText); // Output: ACCOUNT CREATED!

// Get the first paragraph text below it
        String congratsText = getDriver().findElement(By.xpath("//h2[@data-qa='account-created']/following-sibling::p[1]")).getText();
        System.out.println(congratsText); // Output: Congratulations! Your new account has been successfully created!

        getDriver().findElement(By.xpath("//a[@data-qa='continue-button']")).click();
    }


    @And("User deletes account")
    public void userDeletesAccount() {
        getDriver().findElement(By.xpath("//div[@class='shop-menu pull-right']/child::ul/child::li[5]")).click();
    }

    @Then("User verifies account deletion")
    public void userVerifiesAccountDeletion() {
        String accountCreatedText = getDriver().findElement(By.xpath("//h2[@data-qa='account-deleted']")).getText();
        System.out.println(accountCreatedText); // Output: ACCOUNT DELETED!

        // Get the first paragraph text below it
        String congratsText = getDriver().findElement(By.xpath("//h2[@data-qa='account-deleted']/following-sibling::p[1]")).getText();
        System.out.println(congratsText); // Output: Congratulations! Your new account has been successfully created!

//        getDriver().findElement(By.xpath("//a[@data-qa='continue-button']")).click();
    }

    @When ("User enters account details")
    public void userEntersAccountDetails() {
        getDriver().findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("jdough@gmail.com");
        getDriver().findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("j%hnD*ug!");
    }

    @Then("User lands on Home Page")
    public void userLandsOnHomePage() {
        String loggedInText = getDriver().findElement(By.xpath("//i[@class='fa fa-user']/parent::a")).getText();
        System.out.println(loggedInText);
    }

    @And("User clicks on Login button")
    public void userClicksOnLoginButton() {
        getDriver().findElement(By.xpath("//button[@data-qa='login-button']")).click();
    }

    @When("User enters invalid creds {string} and {string}")
    public void userEntersInvalidCredsEmailaddressAndPassword(String invalidEmailAddress, String invalidPassword) {
        getDriver().findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(invalidEmailAddress);
        getDriver().findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(invalidPassword);
    }

    @Then("User observes {string} message")
    public void userObservesErrorMessage(String errorMessage) {
        Assert.assertEquals(getDriver().findElement(By.xpath("//form[@action='/login']/child::p")).getText(), errorMessage);
    }

    @When("User clicks on Logout button")
    public void userClicksOnLogoutButton() {
        getDriver().findElement(By.xpath("//a[@href='/logout']")).click();
    }

    @Then("User is on SignUp-Login Page")
    public void userIsOnSignUpLoginPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.automationexercise.com/login");
    }
}
