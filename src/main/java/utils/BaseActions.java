package utils;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
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

    public static WebElement find(WebElement element) {
        return element;
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

    public static void click(WebElement locator) {
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

    public static WebDriverWait waitUntilVisible(By locator){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(find(locator)));
        return wait;
    }

    public static WebDriverWait waitUntilVisible(WebElement element){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
        return wait;
    }

    public static void scrollIntoView(By locator) {
        WebElement section = BaseActions.find(locator);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", section);
    }

    public static void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public static void validateFormFieldError(By locator, String message) {
        WebElement section = BaseActions.find(locator);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", section);
        Assertions.assertMessageContains(validationMessage, message);
    }

    public static void printTextOfElement(By locator) {
        Logger.info(find(locator).getText());
    }

    public static void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 500);");
    }

    public static void moveToElement(WebElement element){
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).perform();
    }

    public static void waitUntilClickable(WebElement locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForSeconds() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(1));
        wait.until(driver -> true);
    }

    public static void waitUntilElementNotPresent(By locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitUntilURLContains(String loginPage) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains(loginPage));
    }

    public static void checkForAlert() {
        try {
            // Handle any browser alerts
            Alert alert = getDriver().switchTo().alert();
            alert.dismiss();
            System.out.println("Alert dismissed");
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present");
        }
    }

    public static void fluentWaitUntilElementContains(String text) {
        Wait<WebDriver> fluentWait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        fluentWait.until(driver -> driver.getCurrentUrl().contains(text));
    }
}