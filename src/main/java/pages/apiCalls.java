package pages;

import utils.Logger;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class apiCalls {

    private static final String BASE_URL = "https://automationexercise.com/api";
    private static final String PRODUCTS_ENDPOINT = "/productsList";
    private static final String VERIFY_LOGIN = "/verifyLogin";

    public static void verifyLoginEndpoint(String email, String password) {
        Logger.info("Starting API test: verifyLoginEndpoint");

        // Set base URI
        RestAssured.baseURI = BASE_URL;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        // Make POST request to verify login endpoint
        Response response = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParams(requestBody)
                .when()
                .post(VERIFY_LOGIN)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Logger.info("API Response Status Code: " + response.getStatusCode());
        Logger.info("API Response Content-Type: " + response.getContentType());
        Logger.info("The response is: " + response.jsonPath().prettify());

        Logger.info("API test completed successfully");
    }

    public void getAllProductsList() {
        Logger.info("Starting API test: getAllProductsList");

        // Set base URI
        RestAssured.baseURI = BASE_URL;

        // Make GET request to products endpoint
        Response response = given()
                .when()
                .get(PRODUCTS_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Logger.info("API Response Status Code: " + response.getStatusCode());
        Logger.info("API Response Content-Type: " + response.getContentType());
        Logger.info("The response is: " + response.getBody().asString());

        // Verify response contains products array
        response.then()
                .body("products", notNullValue())
                .body("products", not(empty()));

        // Log response details
        Logger.info("Products count in response: " + response.jsonPath().getList("products").size());
        Logger.info("API test completed successfully");
    }
}
