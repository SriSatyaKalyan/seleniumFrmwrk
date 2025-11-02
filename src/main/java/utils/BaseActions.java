package utils;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BaseActions {

    @Getter
    @Setter
    protected static WebDriver driver;

    public static WebElement find(By locator) {
        return getDriver().findElement(locator);
    }

    public static List<WebElement> findAll(By locator) {
        return getDriver().findElements(locator);
    }

    public static WebElement find(WebElement parent, By locator) {
        return parent.findElement(locator);
    }

    public static void click(By locator) {
        find(locator).click();
    }

    public static void click(WebElement parent, By locator) {
        find(parent, locator).click();
    }

    public static void enterIntoElement(By locator, String text) {
        find(locator).sendKeys(text);
    }

    public static void enterIntoElement(WebElement parent, By locator, String text) {
        find(parent, locator).sendKeys(text);
    }

    public static void waitUntilVisible(By locator){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void scrollIntoView(By locator) {
        WebElement section = BaseActions.find(locator);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", section);
    }

    public static void validateFormFieldError(By locator, String message) {
        WebElement section = BaseActions.find(locator);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", section);
        Assertions.assertMessageContains(validationMessage, message);
    }

    public static void printTextOfElement(By headerLoggedInText) {
        // TODO: Needs to be modified. No sout's
        System.out.println(find(headerLoggedInText).getText());
    }

    public static void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 500);");
    }
}