package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JsonDataReader {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Map<String, Object> testData;

    public static void loadTestData(String fileName) {
        try {
            InputStream inputStream = JsonDataReader.class.getClassLoader()
                    .getResourceAsStream("testdata/" + fileName);

            if (inputStream == null) {
                throw new RuntimeException("Could not find file: " + fileName);
            }

            JsonNode jsonNode = objectMapper.readTree(inputStream);
            JsonNode userNode = jsonNode.get("users").get(0);

            testData = new HashMap<>();
            testData.put("name", userNode.get("name").asText());
            testData.put("email", userNode.get("email").asText());
            testData.put("password", userNode.get("password").asText());
            testData.put("title", userNode.get("title").asText());
            testData.put("birth_date", userNode.get("birth_date").asText());
            testData.put("birth_month", userNode.get("birth_month").asText());
            testData.put("birth_year", userNode.get("birth_year").asText());
            testData.put("firstname", userNode.get("firstname").asText());
            testData.put("lastname", userNode.get("lastname").asText());
            testData.put("company", userNode.get("company").asText());
            testData.put("updatedCompany", userNode.get("updatedCompany").asText());
            testData.put("address1", userNode.get("address1").asText());
            testData.put("address2", userNode.get("address2").asText());
            testData.put("country", userNode.get("country").asText());
            testData.put("state", userNode.get("state").asText());
            testData.put("city", userNode.get("city").asText());
            testData.put("zipcode", userNode.get("zipcode").asText());
            testData.put("mobile_number", userNode.get("mobile_number").asText());

            Logger.info("Test data loaded successfully from: " + fileName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data from: " + fileName, e);
        }
    }

    public static String getValue(String key) {
        if (testData == null) {
            throw new RuntimeException("Test data not loaded. Call loadTestData() first.");
        }
        return testData.get(key).toString();
    }

    public static Map<String, Object> getAllData() {
        if (testData == null) {
            throw new RuntimeException("Test data not loaded. Call loadTestData() first.");
        }
        return new HashMap<>(testData);
    }
}