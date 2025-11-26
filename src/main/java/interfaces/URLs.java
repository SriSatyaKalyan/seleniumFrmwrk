package interfaces;

public interface URLs {

    String BASE_URL = "https://www.automationexercise.com";
    String HOME_PAGE = BASE_URL + "/";
    String LOGIN_PAGE = BASE_URL + "/login";
    String PRODUCTS_PAGE = BASE_URL + "/products";
    String CART_PAGE = BASE_URL + "/view_cart";
    String TEST_CASES_PAGE = BASE_URL + "/test_cases";
    String API_TESTING_PAGE = BASE_URL + "/api_list";
    String CONTACT_US_PAGE = BASE_URL + "/contact_us";
    String DELETE_ACCOUNT_PAGE = BASE_URL + "/delete_account";
    String PAYMENT_PAGE = BASE_URL + "/payment";

    String VIDEO_TUTORIALS_PAGE = "https://www.youtube.com/c/AutomationExercise";
    String PAYMENT_DONE = BASE_URL + "/payment_done";

    // API URLs
    String API_BASE_URL = "https://automationexercise.com/api";
    String PRODUCTS_LIST = "/productsList";
    String VERIFY_LOGIN = "/verifyLogin";
    String CREATE_ACCOUNT = "/createAccount";
    String UPDATE_ACCOUNT = "/updateAccount";
    String DELETE_ACCOUNT = "/deleteAccount";

    // External URLs
    String RESET_LOGIN_PAGE = "https://rahulshettyacademy.com/locatorspractice/";
}