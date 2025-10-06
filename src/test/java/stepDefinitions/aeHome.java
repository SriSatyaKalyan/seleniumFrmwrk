package stepDefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class aeHome {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @When("User selects Women category")
    public void userSelectsWomenCategory() {
        getDriver().findElement(By.xpath("//a[@href='#Women']")).click();
    }

    @When("User selects Men category")
    public void userSelectsMenCategory() {
        getDriver().findElement(By.xpath("//a[@href='#Men']")).click();
    }

    @And("User selects {string} category with href {string}")
    public void userSelectsCategoryCategoryWithHrefProduct(String category, String href) {
        System.out.printf("Selecting " + category + " on the Home Page");
        getDriver().findElement(By.xpath("//a[@href='" + href + "']")).click();

    }

    @Then("User observes {string} products")
    public void userObservesProductsProducts(String products) {
        List<WebElement> productNames = getDriver().findElements(By.xpath("//div[@class='single-products']//div[@class='productinfo text-center']/p"));
        for(WebElement product : productNames) {
            System.out.println(product.getText());
        }
    }

    @When("User clicks on View Product for {string}")
    public void userClicksOnViewProductForProduct(String productName) {
        // Verify the text 'Men Tshirt'
//        List<WebElement> products = getDriver().findElements(By.xpath("//div[@class='product-image-wrapper']"));
//        for(WebElement product : products){
//            if(product.findElement(By.xpath("//div[@class='productinfo text-center']/p")).getText().equals(productName)){
//
//            }
//        }
//        Assert.assertEquals("Men Tshirt", productText.getText());
        WebElement productContainer = getDriver().findElement(By.xpath("//div[@class='product-image-wrapper'][.//p[text()='" + productName + "']]"));

// Click View Product within the same container
        productContainer.findElement(By.partialLinkText("View Product")).click();

//// Click on View Product link
//        WebElement viewProductLink = driver.findElement(By.xpath("//div[@class='product-image-wrapper']//a[contains(text(),'View Product')]"));
//        viewProductLink.click();
    }
}