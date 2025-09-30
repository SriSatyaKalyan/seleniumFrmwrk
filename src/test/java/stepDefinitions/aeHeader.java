package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class aeHeader {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    private WebElement getHeader() {
        return getDriver().findElement(By.xpath("//ul[@class='nav navbar-nav']"));
    }

    @When("User clicks on Products option")
    public void userClicksOnProductsOption() {
        getHeader().findElement(By.xpath("//a[@href='/products']")).click();
    }

    @Then("User lands on Products page")
    public void userLandsOnProductsPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.automationexercise.com/products");
    }

    @Given("User clicks on Cart option")
    public void userClicksOnCartOption() {
        getHeader().findElement(By.xpath("//a[@href='/view_cart']")).click();
    }

    @Then("User lands on Cart page")
    public void userLandsOnCartPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.automationexercise.com/view_cart");
    }

    @When("User clicks on TestCases option")
    public void userClicksOnTestCasesOption() {
        getHeader().findElement(By.xpath("//a[@href='/test_cases']")).click();
    }

    @Then("User lands on TestCases page")
    public void userLandsOnTestCasesPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.automationexercise.com/test_cases");
    }

    @When("User clicks on APITesting option")
    public void userClicksOnAPITestingOption() {
        getHeader().findElement(By.xpath("//a[@href='/api_list']")).click();
    }

    @Then("User lands on APITesting page")
    public void userLandsOnAPITestingPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.automationexercise.com/api_list");
    }

    @When("User clicks on VideoTutorials option")
    public void userClicksOnVideoTutorialsOption() {
        getHeader().findElement(By.xpath("//a[@href='https://www.youtube.com/c/AutomationExercise']")).click();
    }

    @Then("User lands on VideoTutorials page")
    public void userLandsOnVideoTutorialsPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.youtube.com/c/AutomationExercise");
    }

    @When("User clicks on ContactUs option")
    public void userClicksOnContactUsOption() {
        getHeader().findElement(By.xpath("//a[@href='/contact_us']")).click();
    }

    @Then("User lands on ContactUs page")
    public void userLandsOnContactUsPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.automationexercise.com/contact_us");
    }

    @When("User navigates to the previous page")
    public void userNavigatesToThePreviousPage() {
        getDriver().navigate().back();
    }
}
