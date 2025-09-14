package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Main {
    /**
     * The main method initializes the Chrome WebDriver, opens the LocatorsPractice page, and quits the driver.
     */
    public static void main(String[] args) {
        // Set the system property to specify the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/Users/srisatyakalyankallepalli/Documents/GitHub/seleniumFrmwrk/chromedriver");
        // Create a new instance of the Chrome browser driver
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://rahulshettyacademy.com/locatorspractice/");

        // Fill in the username and password fields
        driver.findElement(By.id("inputUsername")).sendKeys("contact@rahulshettyacademy.com");
        driver.findElement(By.xpath("//input[@name='inputPassword']")).sendKeys("contact@rahulshettyacademy.com");

        // Click the Sign In button
        driver.findElement(By.xpath("//button[@class='submit signInBtn']")).click();

        // Print the error text
        String errorText = driver.findElement(By.cssSelector("p.error")).getText();
        System.out.println(errorText);

        // Click the Forgot Your Password link
        driver.findElement(By.linkText("Forgot your password?")).click();

        // Fill in the Forgot Password form
        driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("John");
        driver.findElement(By.xpath("//input[@type='text'][2]")).sendKeys("john@doe.com");
        driver.findElement(By.cssSelector("input[type='text']:nth-child(4)")).sendKeys("8888888888");

        // Submit the Forgot Password form
        driver.findElement(By.xpath("//div/button[2]")).click();

        // Print the password help text
        String passWordHelpText = driver.findElement(By.cssSelector("form p")).getText();
        System.out.println(passWordHelpText);

        // Close the browser and end the WebDriver session
        driver.quit();
    }
}