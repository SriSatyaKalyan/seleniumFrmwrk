package stepDefinitions;

import interfaces.HomePageLocators;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.HeaderSection;
import utils.Assertions;
import utils.BaseActions;
import utils.URLs;

public class aeHeader {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    public HeaderSection header = new HeaderSection(getDriver());

    @When("User clicks on Products option")
    public void userClicksOnProductsOption() {
        header.clickProductsOption();
    }

    @Then("User lands on Products page")
    public void userLandsOnProductsPage() {
        Assertions.assertCurrentUrl(getDriver(), URLs.PRODUCTS_PAGE);
    }

    @Given("User clicks on Cart option")
    public void userClicksOnCartOption() {
        BaseActions.waitUntilVisible(By.xpath(HomePageLocators.HEADER_CART));
        header.clickCartOption();
    }

    @Then("User lands on Cart page")
    public void userLandsOnCartPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), URLs.CART_PAGE);
    }

    @When("User clicks on TestCases option")
    public void userClicksOnTestCasesOption() {
        header.clickTestCasesOption();
    }

    @Then("User lands on TestCases page")
    public void userLandsOnTestCasesPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), URLs.TEST_CASES_PAGE);
    }

    @When("User clicks on APITesting option")
    public void userClicksOnAPITestingOption() {
        header.clickApiTestingOption();
    }

    @Then("User lands on APITesting page")
    public void userLandsOnAPITestingPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), URLs.API_TESTING_PAGE);
    }

    @When("User clicks on VideoTutorials option")
    public void userClicksOnVideoTutorialsOption() {
        header.clickVideoTutorialsOption();
    }

    @Then("User lands on VideoTutorials page")
    public void userLandsOnVideoTutorialsPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), URLs.VIDEO_TUTORIALS_PAGE);
    }

    @When("User clicks on ContactUs option")
    public void userClicksOnContactUsOption() {
        header.clickContactUsOption();
    }

    @Then("User lands on ContactUs page")
    public void userLandsOnContactUsPage() {
        Assert.assertEquals(getDriver().getCurrentUrl(), URLs.CONTACT_US_PAGE);
    }

    @When("User navigates to the previous page")
    public void userNavigatesToThePreviousPage() {
        BaseActions.navigateBack();
    }
}
