package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class testBase {

    protected static WebDriver driver;

    @Before
    public void setUp() {
        if (driver == null) {
            // Clear cache and setup latest ChromeDriver
            WebDriverManager.chromedriver()
                    .clearDriverCache()
                    .clearResolutionCache()
                    .setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-web-security");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");

            // Initialize the WebDriver with options
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

//    @After
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//            driver = null;
//        }
//    }

    public static WebDriver getDriver() {
        return driver;
    }
}
