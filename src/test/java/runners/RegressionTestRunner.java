package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/cucumber/features",
        glue = "stepDefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/regression-tests.html",
                "json:target/cucumber-reports/regression-tests.json",
                "junit:target/cucumber-reports/regression-tests.xml"
        },
        tags = "@regression",
        monochrome = true,
        publish = false
)
public class RegressionTestRunner extends AbstractTestNGCucumberTests {
}