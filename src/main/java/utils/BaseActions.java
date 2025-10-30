package utils;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
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

    public static void click(By locator) {
        find(locator).click();
    }

    public static void waitUntilVisible(By locator){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void navigateBack() {
        getDriver().navigate().back();
    }
}