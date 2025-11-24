package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/cucumber/features",
        glue = "stepDefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/all-tests.html",
                "json:target/cucumber-reports/all-tests.json",
                "junit:target/cucumber-reports/all-tests.xml"
        },
        monochrome = true,
        tags = "@smoke or @regression or @functional",
        publish = false
)
public class AllTestRunner extends AbstractTestNGCucumberTests {
}