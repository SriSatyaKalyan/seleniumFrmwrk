package utils;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

public abstract class Navigations {

    @Getter
    @Setter
    protected static WebDriver driver;

    public static void navigateTo(String url){
        getDriver().get(url);
    }

    public static void navigateBack() {
        getDriver().navigate().back();
    }
}
