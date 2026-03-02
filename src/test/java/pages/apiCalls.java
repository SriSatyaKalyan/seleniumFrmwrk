package pages;

import interfaces.URLs;
import utils.Logger;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class apiCalls implements URLs {

    public static void verifyLoginEndpoint(String email, String password) {
        Logger.info("Starting API test: verifyLoginEndpoint");

        // Set base URI
        RestAssured.baseURI = API_BASE_URL;

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
        RestAssured.baseURI = API_BASE_URL;

        // Make GET request to products endpoint
        Response response = given()
                .when()
                .get(PRODUCTS_LIST)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Logger.info("API Response Status Code: " + response.getStatusCode());
        Logger.info("API Response Content-Type: " + response.getContentType());
        Logger.info("The response is: " + response.jsonPath().prettify());

        // Verify response contains products array
        response.then()
                .body("products", notNullValue())
                .body("products", not(empty()));

        // Log response details
        Logger.info("Products count in response: " + response.jsonPath().getList("products").size());
        Logger.info("API test completed successfully");
    }

    public static void createAccountEndpoint(String name, String email, String password, String title,
                                           String birthDate, String birthMonth, String birthYear,
                                           String firstname, String lastname, String company,
                                           String address1, String address2, String country,
                                           String state, String city, String zipcode, String mobileNumber) {
        Logger.info("Starting API test: createAccountEndpoint");

        // Set base URI
        RestAssured.baseURI = API_BASE_URL;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("title", title);
        requestBody.put("birth_date", birthDate);
        requestBody.put("birth_month", birthMonth);
        requestBody.put("birth_year", birthYear);
        requestBody.put("firstname", firstname);
        requestBody.put("lastname", lastname);
        requestBody.put("company", company);
        requestBody.put("address1", address1);
        requestBody.put("address2", address2);
        requestBody.put("country", country);
        requestBody.put("state", state);
        requestBody.put("city", city);
        requestBody.put("zipcode", zipcode);
        requestBody.put("mobile_number", mobileNumber);

        // Make POST request to create account endpoint
        Response response = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParams(requestBody)
                .when()
                .post(CREATE_ACCOUNT)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .extract()
                .response();

        Logger.info("API Response Status Code: " + response.getStatusCode());
        Logger.info("API Response Content-Type: " + response.getContentType());
        Logger.info("The response is: " + response.jsonPath().prettify());

        Logger.info("API test completed successfully");
    }

    public static void updateAccountEndpoint(String name, String email, String password, String title,
                                           String birthDate, String birthMonth, String birthYear,
                                           String firstname, String lastname, String company,
                                           String address1, String address2, String country,
                                           String state, String city, String zipcode, String mobileNumber) {
        Logger.info("Starting API test: updateAccountEndpoint");

        // Set base URI
        RestAssured.baseURI = API_BASE_URL;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("title", title);
        requestBody.put("birth_date", birthDate);
        requestBody.put("birth_month", birthMonth);
        requestBody.put("birth_year", birthYear);
        requestBody.put("firstname", firstname);
        requestBody.put("lastname", lastname);
        requestBody.put("company", company);
        requestBody.put("address1", address1);
        requestBody.put("address2", address2);
        requestBody.put("country", country);
        requestBody.put("state", state);
        requestBody.put("city", city);
        requestBody.put("zipcode", zipcode);
        requestBody.put("mobile_number", mobileNumber);

        // Make PUT request to update account endpoint
        Response response = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParams(requestBody)
                .when()
                .put(UPDATE_ACCOUNT)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .extract()
                .response();

        Logger.info("API Response Status Code: " + response.getStatusCode());
        Logger.info("API Response Content-Type: " + response.getContentType());
        Logger.info("The response is: " + response.jsonPath().prettify());

        Logger.info("API test completed successfully");
    }

    public static void deleteAccountEndpoint(String email, String password) {
        Logger.info("Starting API test: deleteAccountEndpoint");

        // Set base URI
        RestAssured.baseURI = API_BASE_URL;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        // Make POST request to verify login endpoint
        Response response = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParams(requestBody)
                .when()
                .delete(DELETE_ACCOUNT)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Logger.info("API Response Status Code: " + response.getStatusCode());
        Logger.info("API Response Content-Type: " + response.getContentType());
        Logger.info("The response is: " + response.jsonPath().prettify());

        Logger.info("API test completed successfully");
    }
}
