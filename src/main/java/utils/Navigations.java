package utils;

import static utils.BaseActions.driver;

public abstract class Navigations {

    public static void navigateTo(String url){
        driver.get(url);
    }

    public static void navigateBack() {
        driver.navigate().back();
    }
}