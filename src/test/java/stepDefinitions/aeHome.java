package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.HomePage;

// TODO: Move signup actions into its own page - currently in LoginPage
// TODO: See code analysis actions

public class aeHome {

    public HomePage home = new HomePage(getDriver());

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @When("User selects Women category")
    public void userSelectsWomenCategory() {
        home.clickWomenCategory();
    }

    @When("User selects Men category")
    public void userSelectsMenCategory() {
        home.clickMenCategory();
    }

    @And("User selects {string} category with href {string}")
    public void userSelectsCategoryCategoryWithHrefProduct(String category, String href) {
        home.selectGenderSpecificCategory(category, href);
    }

    @Then("User observes {string} products")
    public void userObservesProducts(String products) {
        home.userObservesProducts(products);
    }

    @When("User clicks on View Product for {string}")
    public void userClicksOnViewProduct(String productName) {
        home.clickOnViewProduct(productName);
    }
}