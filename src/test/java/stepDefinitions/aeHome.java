package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.HomePage;

public class aeHome {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    public HomePage homePage = new HomePage(getDriver());

    @When("User selects Women category")
    public void userSelectsWomenCategory() {
        homePage.clickWomenCategory();
    }

    @When("User selects Men category")
    public void userSelectsMenCategory() {
        homePage.clickMenCategory();
    }

    @And("User selects {string} category with href {string}")
    public void userSelectsCategoryCategoryWithHrefProduct(String category, String href) {
        homePage.selectGenderSpecificCategory(category, href);
    }

    @Then("User observes {string} products")
    public void userObservesProducts(String products) {
        homePage.userObservesProducts(products);
    }

    @When("User clicks on View Product for {string}")
    public void userClicksOnViewProductForProduct(String productName) {
        WebElement productContainer = getDriver().findElement(By.xpath("//div[@class='product-image-wrapper'][.//p[text()='" + productName + "']]"));
        productContainer.findElement(By.xpath(".//a[contains(text(), 'View Product')]")).click();
    }
}