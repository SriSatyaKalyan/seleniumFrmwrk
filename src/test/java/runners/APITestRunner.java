package runners;

import org.testng.annotations.Test;

public class APITestRunner {

    @Test
    public void runAPITests() {
        // TestNG will automatically discover and run all @Test methods in apiTests package
        // This runner can be used to group API tests if needed
    }
}