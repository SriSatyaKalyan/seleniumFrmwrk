package stepDefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

public class aeItemPage {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @Then("User observes Review section")
    public void userObservesReviewSection() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            WebElement reviewForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='review-form']")));
            Assert.assertTrue(reviewForm.isDisplayed());
        } catch (TimeoutException e) {
            Assert.fail("Review section not found within 10 seconds");
        }
    }

    @Then("User observes Product Details {string}, {string}, {string}, {string}, {string} and {string}")
    public void userObservesProductDetails(String name, String category, String cost, String availability, String condition, String brand) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement productInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='product-information']")));

//         Verify product name (exact text match)
//        WebElement nameElement = productInfo.findElement(By.xpath("//h2[text()='" + name + "']"));
//        Assert.assertTrue("Product name '" + name + "' not displayed", nameElement.isDisplayed());

        // Verify category (starts with "Category: " followed by category)
        WebElement categoryElement = productInfo.findElement(By.xpath(".//p[starts-with(text(), 'Category: " + category + "')]"));
        Assert.assertTrue("Category '" + category + "' not displayed", categoryElement.isDisplayed());

        // Verify cost (exact text match for "Rs. {cost}")
        WebElement costElement = productInfo.findElement(By.xpath(".//span/span[text()='Rs. " + cost + "']"));
        Assert.assertTrue("Cost 'Rs. " + cost + "' not displayed", costElement.isDisplayed());

        // Verify availability (bold label with text following)
        WebElement availabilityElement = productInfo.findElement(By.xpath(".//p[b[text()='Availability:'] and contains(text(), '" + availability + "')]"));
        Assert.assertTrue("Availability '" + availability + "' not displayed", availabilityElement.isDisplayed());

        // Verify condition (bold label with text following)
        WebElement conditionElement = productInfo.findElement(By.xpath(".//p[b[text()='Condition:'] and contains(text(), '" + condition + "')]"));
        Assert.assertTrue("Condition '" + condition + "' not displayed", conditionElement.isDisplayed());

        // Verify brand (bold label with text following, handle HTML entities like &amp;)
        String brandXpath = ".//p[b[text()='Brand:'] and (contains(text(), '" + brand + "') or contains(text(), '" + brand.replace("&", "&amp;") + "'))]";
        WebElement brandElement = productInfo.findElement(By.xpath(brandXpath));
        Assert.assertTrue("Brand '" + brand + "' not displayed", brandElement.isDisplayed());
    }

    @When("User searches for {string}")
    public void userSearchesForProduct(String product) {
        getDriver().findElement(By.xpath("//input[@id='search_product']")).sendKeys(product);
        getDriver().findElement(By.xpath("//button[@id='submit_search']")).click();
    }
}
