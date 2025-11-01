package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Assertions {
    public static void assertCurrentUrl(WebDriver driver, String expectedUrl) {
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
    }

    public static void assertDisplayed(WebElement element) {
        Assert.assertTrue(element.isDisplayed());
    }

    public static void assertTextInElement(WebElement element, String message) {
        Assert.assertTrue(element.isDisplayed());
        Assert.assertEquals(message, element.getText());
    }

    public static void assertMessageContains(String messageOne, String messageTwo){
        Assert.assertTrue(messageOne != null && messageOne.contains(messageTwo));
    }
}
