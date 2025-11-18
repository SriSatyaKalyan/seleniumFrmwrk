package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/cucumber/features",
        glue = "stepDefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/smoke-tests.html",
                "json:target/cucumber-reports/smoke-tests.json",
                "junit:target/cucumber-reports/smoke-tests.xml"
        },
        tags = "@smoke",
        monochrome = true,
        publish = false
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {
}