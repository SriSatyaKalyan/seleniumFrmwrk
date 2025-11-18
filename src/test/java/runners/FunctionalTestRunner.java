package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/cucumber/features",
        glue = "stepDefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/functional-tests.html",
                "json:target/cucumber-reports/functional-tests.json",
                "junit:target/cucumber-reports/functional-tests.xml"
        },
        tags = "@functional",
        monochrome = true,
        publish = false
)
public class FunctionalTestRunner extends AbstractTestNGCucumberTests {
}