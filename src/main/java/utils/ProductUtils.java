package utils;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductUtils {

    public static boolean validateProducts(String expectedProducts, List<WebElement> actualProductElements) {
        List<String> expectedProductList = parseExpectedProducts(expectedProducts);
        List<String> actualProductList = extractProductNames(actualProductElements);

        return compareProductLists(expectedProductList, actualProductList);
    }

    public static List<String> parseExpectedProducts(String products) {
        if (products == null || products.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String[] productArray = products.split(",");
        List<String> productList = new ArrayList<>();

        for (String product : productArray) {
            productList.add(product.trim().toLowerCase());
        }

        return productList;
    }

    public static List<String> extractProductNames(List<WebElement> productElements) {
        List<String> productNames = new ArrayList<>();

        for (WebElement element : productElements) {
            String productName = element.getText().trim().toLowerCase();
            if (!productName.isEmpty()) {
                productNames.add(productName);
            }
        }

        return productNames;
    }

    public static boolean compareProductLists(List<String> expectedProducts, List<String> actualProducts) {
        if (expectedProducts.isEmpty()) {
            return !actualProducts.isEmpty();
        }

        for (String expectedProduct : expectedProducts) {
            boolean found = false;
            for (String actualProduct : actualProducts) {
                if (actualProduct.contains(expectedProduct) || expectedProduct.contains(actualProduct)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Expected product not found: " + expectedProduct);
                return false;
            }
        }

        return true;
    }

    public static void printProductComparison(String expectedProducts, List<WebElement> actualProductElements) {
        List<String> expectedProductList = parseExpectedProducts(expectedProducts);
        List<String> actualProductList = extractProductNames(actualProductElements);

        System.out.println("\n=== Product Validation ===");
        System.out.println("Expected products: " + expectedProductList);
        System.out.println("Actual products found: " + actualProductList);
        System.out.println("Validation result: " + compareProductLists(expectedProductList, actualProductList));
        System.out.println("===========================");
    }
}