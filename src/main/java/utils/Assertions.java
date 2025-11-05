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

    public static void assertQuantityInElement(WebElement element, String number){
        Assert.assertTrue(element.isDisplayed());
        Assert.assertEquals(Integer.parseInt(element.getText()), Integer.parseInt(number));
    }

    public static void assertMessageContains(String messageOne, String messageTwo){
        Assert.assertTrue(messageOne != null && messageOne.contains(messageTwo));
    }

    public static void assertAttributeInElement(WebElement element, String attribute, String value) {
        Assert.assertEquals(element.getAttribute(attribute), value);
    }

    public static void assertCheckBoxSelected(WebElement element) {
        Assert.assertTrue(element.isSelected());
    }

    public static void assertCheckBoxUnSelected(WebElement element) {
        Assert.assertFalse(element.isSelected());
    }

    public static void assertTextInElementContains(WebElement element, String text) {
        Assert.assertTrue(element.isDisplayed());
        Assert.assertTrue(element.getText().contains(text));
    }

    public static void assertCurrentUrlContains(WebDriver driver, String expectedUrl) {
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedUrl));
    }
}
