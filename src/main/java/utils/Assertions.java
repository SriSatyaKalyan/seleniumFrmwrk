package utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Assertions {

    public static void assertCurrentUrl(WebDriver driver, String expectedUrl) {
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
    }
}
