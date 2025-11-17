package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.ProductPage;
import pages.HomePage;

public class aeItemPage {

    public ProductPage product = new ProductPage(getDriver());
    public HomePage homePage = new HomePage(getDriver());

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @Then("User observes Review section")
    public void userObservesReviewSection() {
        product.observeReviewSection();
    }

    @Then("User observes Product Details {string}, {string}, {string}, {string}, {string} and {string}")
    public void userObservesProductDetails(String name, String category, String cost, String availability, String condition, String brand) {
        product.observeProductsDeets(name, category, cost, availability, condition, brand);
    }

    @When("User searches for {string}")
    public void userSearchesForProduct(String product) {
        homePage.searchForProduct(product);
    }
}
