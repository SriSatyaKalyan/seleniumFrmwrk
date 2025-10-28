package pages;

import interfaces.PageActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

import java.time.Duration;

/**
 * Base page class containing common functionality for all pages
 * Demonstrates: Inheritance, Encapsulation, and Abstraction
 */
public abstract class BasePage {
    protected final WebDriver driver;

    protected BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver must not be null");
        }
        this.driver = driver;
    }

    protected WebDriver getDriver() {
        return driver;
    }
}