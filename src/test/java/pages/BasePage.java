package pages;

import org.openqa.selenium.WebDriver;
import utils.Logger;

/**
 * Base page class containing common functionality for all pages
 * Demonstrates: Inheritance, Encapsulation, and Abstraction
 */
public abstract class BasePage {
    protected final WebDriver driver;

    protected BasePage(WebDriver driver) {
        Logger.debug("Initializing BasePage with WebDriver: {}", driver != null ? driver.getClass().getSimpleName() : "null");
        if (driver == null) {
            Logger.error("WebDriver initialization failed - WebDriver is null");
            throw new IllegalArgumentException("WebDriver must not be null");
        }
        this.driver = driver;
        Logger.info("BasePage successfully initialized for class: {}", this.getClass().getSimpleName());
    }

    protected WebDriver getDriver() {
        return driver;
    }
}