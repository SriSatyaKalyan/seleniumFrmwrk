package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Main {
    /**
     * The main method initializes the Chrome WebDriver, opens Google, prints the page title, and quits the driver.
     */
    public static void main(String[] args) throws InterruptedException {
        // Set the system property to specify the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/Users/srisatyakalyankallepalli/Documents/GitHub/seleniumFrmwrk/chromedriver");
        // Create a new instance of the Chrome browser driver
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://rahulshettyacademy.com/locatorspractice/");

        // Print the title of the current page to the console
        System.out.println(driver.getTitle());

        driver.findElement(By.id("inputUsername")).sendKeys("contact@rahulshettyacademy.com");
        driver.findElement(By.xpath("//input[@name='inputPassword']")).sendKeys("contact@rahulshettyacademy.com");
        driver.findElement(By.xpath("//button[@class='submit signInBtn']")).click();

        String errorText = driver.findElement(By.cssSelector("p.error")).getText();
        System.out.println(errorText);

        driver.findElement(By.linkText("Forgot your password?")).click();

        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("John");
        driver.findElement(By.xpath("//input[@type='text'][2]")).sendKeys("john@doe.com");
        driver.findElement(By.cssSelector("input[type='text']:nth-child(4)")).sendKeys("8888888888");

        //parent-child traversal in xpath
        driver.findElement(By.xpath("//div/button[2]")).click();

        //parent-child traversal in css
        String passWordHelpText = driver.findElement(By.cssSelector("form p")).getText();
        System.out.println(passWordHelpText);

        // Close the browser and end the WebDriver session
        driver.quit();
    }
}