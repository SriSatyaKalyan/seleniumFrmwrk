package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * DataManager - Utility class for managing test data from JSON files
 * Provides centralized access to user, product, and message data
 */
public class DataManager {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static JsonNode usersData;
    private static JsonNode productsData;
    private static JsonNode messagesData;

    static {
        loadTestData();
    }

    private static void loadTestData() {
        try {
            usersData = loadJsonResource("/testdata/users.json");
            productsData = loadJsonResource("/testdata/products.json");
            messagesData = loadJsonResource("/testdata/messages.json");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data files", e);
        }
    }

    private static JsonNode loadJsonResource(String resourcePath) throws IOException {
        InputStream inputStream = DataManager.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return objectMapper.readTree(inputStream);
    }

    // User Data Methods
    public static UserData getValidUser(String userKey) {
        JsonNode userNode = usersData.path("validUsers").path(userKey);
        if (userNode.isMissingNode()) {
            throw new IllegalArgumentException("Valid user not found: " + userKey);
        }
        return new UserData(userNode);
    }

    public static UserData getInvalidUser(String userKey) {
        JsonNode userNode = usersData.path("invalidUsers").path(userKey);
        if (userNode.isMissingNode()) {
            throw new IllegalArgumentException("Invalid user not found: " + userKey);
        }
        return new UserData(userNode);
    }

    public static ContactData getContactUser(String userKey) {
        JsonNode userNode = usersData.path("contactUsers").path(userKey);
        if (userNode.isMissingNode()) {
            throw new IllegalArgumentException("Contact user not found: " + userKey);
        }
        return new ContactData(userNode);
    }

    public static String getSubscriptionEmail(String emailType) {
        return usersData.path("subscriptionUsers").path(emailType).asText();
    }

    // Product Data Methods
    public static ProductData getProduct(String productKey) {
        JsonNode productNode = productsData.path("products").path(productKey);
        if (productNode.isMissingNode()) {
            throw new IllegalArgumentException("Product not found: " + productKey);
        }
        return new ProductData(productNode);
    }

    public static CategoryData getCategory(String gender, String categoryType) {
        JsonNode categoryNode = productsData.path("categories").path(gender).path(categoryType);
        if (categoryNode.isMissingNode()) {
            throw new IllegalArgumentException("Category not found: " + gender + "/" + categoryType);
        }
        return new CategoryData(categoryNode);
    }

    public static List<String> getCommonProducts() {
        List<String> products = new ArrayList<>();
        JsonNode commonProductsNode = productsData.path("commonProducts");
        for (JsonNode product : commonProductsNode) {
            products.add(product.asText());
        }
        return products;
    }

    // Message Data Methods
    public static String getErrorMessage(String messageKey) {
        return messagesData.path("errorMessages").path(messageKey).asText();
    }

    public static String getSuccessMessage(String messageKey) {
        return messagesData.path("successMessages").path(messageKey).asText();
    }

    public static String getOrderComment(String commentKey) {
        return messagesData.path("orderComments").path(commentKey).asText();
    }

    // Data Classes
    public static class UserData {
        private final JsonNode userNode;

        public UserData(JsonNode userNode) {
            this.userNode = userNode;
        }

        public String getName() { return userNode.path("name").asText(); }
        public String getEmail() { return userNode.path("email").asText(); }
        public String getPassword() { return userNode.path("password").asText(); }
        public String getFirstName() { return userNode.path("personalInfo").path("firstName").asText(); }
        public String getLastName() { return userNode.path("personalInfo").path("lastName").asText(); }
        public String getDobDay() { return userNode.path("personalInfo").path("dateOfBirth").path("day").asText(); }
        public String getDobMonth() { return userNode.path("personalInfo").path("dateOfBirth").path("month").asText(); }
        public String getDobYear() { return userNode.path("personalInfo").path("dateOfBirth").path("year").asText(); }
        public boolean getNewsletter() { return userNode.path("personalInfo").path("newsletter").asBoolean(); }
        public String getCompany() { return userNode.path("address").path("company").asText(); }
        public String getAddress1() { return userNode.path("address").path("address1").asText(); }
        public String getAddress2() { return userNode.path("address").path("address2").asText(); }
        public String getState() { return userNode.path("address").path("state").asText(); }
        public String getCity() { return userNode.path("address").path("city").asText(); }
        public String getZipcode() { return userNode.path("address").path("zipcode").asText(); }
        public String getCountry() { return userNode.path("address").path("country").asText(); }
        public String getMobileNumber() { return userNode.path("address").path("mobileNumber").asText(); }
        public String getCardNumber() { return userNode.path("paymentInfo").path("cardNumber").asText(); }
        public String getCvc() { return userNode.path("paymentInfo").path("cvc").asText(); }
        public String getExpiryMonth() { return userNode.path("paymentInfo").path("expiryMonth").asText(); }
        public String getExpiryYear() { return userNode.path("paymentInfo").path("expiryYear").asText(); }
    }

    public static class ContactData {
        private final JsonNode contactNode;

        public ContactData(JsonNode contactNode) {
            this.contactNode = contactNode;
        }

        public String getName() { return contactNode.path("name").asText(); }
        public String getEmail() { return contactNode.path("email").asText(); }
        public String getSubject() { return contactNode.path("subject").asText(); }
        public String getMessage() { return contactNode.path("message").asText(); }
    }

    public static class ProductData {
        private final JsonNode productNode;

        public ProductData(JsonNode productNode) {
            this.productNode = productNode;
        }

        public String getName() { return productNode.path("name").asText(); }
        public String getCategory() { return productNode.path("category").asText(); }
        public String getPrice() { return productNode.path("price").asText(); }
        public String getAvailability() { return productNode.path("availability").asText(); }
        public String getCondition() { return productNode.path("condition").asText(); }
        public String getBrand() { return productNode.path("brand").asText(); }
    }

    public static class CategoryData {
        private final JsonNode categoryNode;

        public CategoryData(JsonNode categoryNode) {
            this.categoryNode = categoryNode;
        }

        public String getHref() { return categoryNode.path("href").asText(); }
        public List<String> getProducts() {
            List<String> products = new ArrayList<>();
            JsonNode productsNode = categoryNode.path("products");
            for (JsonNode product : productsNode) {
                products.add(product.asText());
            }
            return products;
        }
        public String getProductsAsString() {
            return String.join(", ", getProducts());
        }
    }
}