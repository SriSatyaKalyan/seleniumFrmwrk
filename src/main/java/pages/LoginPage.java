package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Login Page Object Model class
 * Demonstrates: Inheritance, Encapsulation, and Polymorphism
 */
public class LoginPage extends BasePage {

    // Encapsulated page elements using @FindBy annotations
    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@name='inputPassword']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@class='submit signInBtn']")
    private WebElement signInButton;

    @FindBy(css = "p.error")
    private WebElement errorMessage;

    @FindBy(linkText = "Forgot your password?")
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//p[contains(text(),'You are successfully logged in')]")
    private WebElement successMessage;

    // Constructor calling parent constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Implementation of abstract method from parent class
    @Override
    public boolean isPageLoaded() {
        return isElementVisible(usernameField) && isElementVisible(passwordField);
    }

    // Implementation of interface method with page-specific behavior
    @Override
    public void navigateToPage() {
        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        waitForPageLoad();
    }

    // Encapsulated page actions - business logic methods
    public void enterUsername(String username) {
        enterText(usernameField, username);
    }

    public void enterPassword(String password) {
        enterText(passwordField, password);
    }

    public void clickSignIn() {
        clickElement(signInButton);
    }

    public void clickForgotPassword() {
        clickElement(forgotPasswordLink);
        // Return new page object - demonstrates object creation
        // Could return ForgotPasswordPage object here
    }

    // Method combining multiple actions - demonstrates abstraction
    public void loginWith(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
    }

    // Verification methods
    public String getErrorMessage() {
        return getElementText(errorMessage);
    }

    public boolean isLoginSuccessful() {
        return isElementVisible(successMessage);
    }

    public String getSuccessMessage() {
        return getElementText(successMessage);
    }

    // Method demonstrating polymorphism - can be overridden by child classes
    public void performLogin(String username, String password) {
        navigateToPage();
        loginWith(username, password);
    }
}