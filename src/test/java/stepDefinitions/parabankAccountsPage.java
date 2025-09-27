package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Objects;

public class parabankAccountsPage {

    private WebDriver getDriver() {
        return testBase.getDriver();
    }

    @Then("User is on Accounts Overview Page")
    public void userLandsOnContactPage() throws InterruptedException {

        System.out.println(getDriver().getTitle());
        Assert.assertTrue((getDriver().getTitle()).contains("ParaBank | Accounts Overview"));
    }

    @Given("User extracts Account number")
    public void userExtractsAccountNumber() {
        WebElement table = getDriver().findElement(By.id("accountTable"));
        WebElement tbody = table.findElement(By.tagName("tbody"));
        WebElement firstRow = tbody.findElement(By.xpath("./tr[1]"));
        WebElement accountCell = firstRow.findElement(By.xpath("./td[1]")); // First column
        String accountNumber = accountCell.getText(); // Returns "17229"
        System.out.println("The account number is: " + accountNumber);
    }
}
