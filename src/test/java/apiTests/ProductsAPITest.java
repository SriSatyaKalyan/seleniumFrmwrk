package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductsAPITest {

    private static final String BASE_URL = "https://automationexercise.com/api";
    private static final String PRODUCTS_ENDPOINT = "/productsList";

    @Test(description = "Verify Get All Products List API returns 200 and products data")
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