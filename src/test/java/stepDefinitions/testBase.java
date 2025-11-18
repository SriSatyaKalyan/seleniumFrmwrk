package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class testBase {

    @Getter
    protected static WebDriver driver;

    @Before
    public void setUp() {

        // Suppress WebDriverManager logs
        Logger.getLogger("io.github.bonigarcia.wdm").setLevel(Level.SEVERE);

        // Suppress Selenium DevTools logs
        Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.SEVERE);

        // Always ensure clean state - quit existing driver if any
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // Ignore quit errors
            }
            driver = null;
        }

        // Clear cache and setup latest ChromeDriver
        WebDriverManager.chromedriver()
//                .clearDriverCache()
//                .clearResolutionCache()
                .setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        // Check for browser mode from system property or environment variable
        String browserMode = System.getProperty("browser", System.getenv("BROWSER_MODE"));
        if (browserMode != null && (browserMode.contains("headless") || browserMode.equals("chrome-headless"))) {
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-renderer-backgrounding");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // Disable password saving
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // Initialize the WebDriver with options
        driver = new ChromeDriver(options);

        // Set comprehensive timeout configurations
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
