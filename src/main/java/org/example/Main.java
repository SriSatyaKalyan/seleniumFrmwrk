package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.time.Duration;

public class Main {
    /**
     * The main method initializes the Chrome WebDriver, opens the LocatorsPractice page, and quits the driver.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // Set the system property to specify the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/Users/srisatyakalyankallepalli/Documents/GitHub/seleniumFrmwrk/chromedriver");
        // Create a new instance of the Chrome browser driver
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("https://www.automationexercise.com/");
        driver.quit();
    }
}