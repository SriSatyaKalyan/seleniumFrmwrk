package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;

public class testBase {

    protected static WebDriver driver;

    @Before
    public void setUp() {

        // Suppress WebDriverManager logs
        Logger.getLogger("io.github.bonigarcia.wdm").setLevel(Level.SEVERE);

        // Suppress Selenium DevTools logs
        Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.SEVERE);

        if (driver == null) {
            // Clear cache and setup latest ChromeDriver
            WebDriverManager.chromedriver()
//                    .clearDriverCache()
//                    .clearResolutionCache()
                    .setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-web-security");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
//            options.addArguments("--headless");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("useAutomationExtension", false);

            // Disable password saving
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            // Initialize the WebDriver with options
            driver = new ChromeDriver(options);

            // Set comprehensive timeout configurations
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
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
