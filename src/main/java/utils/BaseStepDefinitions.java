package utils;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class BaseStepDefinitions {

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

}
