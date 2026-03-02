package utils;

import java.util.List;

/**
 * TestDataResolver - Utility class for resolving test data from JSON files
 * Provides reusable methods for converting data keys to actual values
 */
public class TestDataResolver {

    /**
     * Resolves product data from a given product key
     * @param productKey The key to resolve (e.g., "commonProducts", "blueTop")
     * @return Comma-separated product names or single product name
     */
    public static String resolveProductsFromData(String productKey) {
        if ("commonProducts".equals(productKey)) {
            List<String> productList = DataManager.getCommonProducts();
            return String.join(", ", productList);
        } else {
            DataManager.ProductData productData = DataManager.getProduct(productKey);
            return productData.getName();
        }
    }

    /**
     * Resolves user data from a given user key
     * @param userKey The user key to resolve (e.g., "johnDough", "maryJain")
     * @return UserData object containing all user information
     */
    public static DataManager.UserData resolveUserFromData(String userKey) {
        return DataManager.getValidUser(userKey);
    }

    /**
     * Formats user name with title prefix
     * @param userData The user data object
     * @return Formatted name with "Mr." prefix
     */
    public static String formatUserNameWithTitle(DataManager.UserData userData) {
        return "Mr. " + userData.getName();
    }
}