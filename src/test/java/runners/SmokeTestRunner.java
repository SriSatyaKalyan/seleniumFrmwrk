package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/cucumber/features",
        glue = "stepDefinitions",
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/cucumber-reports/smoke-tests.html",
                "json:target/cucumber-reports/smoke-tests.json",
                "junit:target/cucumber-reports/smoke-tests.xml"
        },
        monochrome = true,
        publish = false
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {
}