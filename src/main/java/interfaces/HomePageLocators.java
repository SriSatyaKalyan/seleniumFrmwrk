package interfaces;

public interface HomePageLocators {

    //HEADER
    String HEADER_SECTION = "//ul[@class='nav navbar-nav']";
    String HEADER_PRODUCTS = "//a[@href='/products']";
    String HEADER_CART = "//a[@href='/view_cart']";
    String HEADER_TESTCASES = "//a[@href='/test_cases']";
    String HEADER_APILIST = "//a[@href='/api_list']";
    String HEADER_CONTACTUS = "//a[@href='/contact_us']";
    String HEADER_VIDEOTUTORIALS = "//a[@href='https://www.youtube.com/c/AutomationExercise']";
    String HEADER_SINGUP_LOGIN = "//a[@href='/login']";
    String HEADER_LOGOUT = "//a[@href='/logout']";
    String HEADER_LOGGED_IN_TEXT = "//i[@class='fa fa-user']/parent::a";
    String HEADER_DELETE_ACCOUNT = "//div[@class='shop-menu pull-right']/child::ul/child::li[5]";

    String WOMEN_CATEGORY_XPATH = "//a[@href='#Women']";
    String MEN_CATEGORY_XPATH = "//a[@href='#Men']";

    static String PRODUCT_SPECIFIC_CONTAINER(String productName){
        return "//div[@class='product-image-wrapper'][.//p[text()='" + productName + "']]";
    }
    String VIEW_PRODUCT = ".//a[contains(text(), 'View Product')]";

    static String GENDER_SPECIFIC_CATEGORY_XPATH(String href) {
        return "//a[@href='" + href + "']";
    }
    String PRODUCTS_LIST = "//div[@class='single-products']//div[@class='productinfo text-center']/p";

    // FOOTER
    String FOOTER_SUBSCRIPTION_SECTION = "//div[@class='single-widget']";
    String SUBSCRIPTION_SEARCH_FORM = "//form[@class='searchform']";
    String EMAIL_INPUT = "susbscribe_email";
    String SUBSCRIBE_BUTTON = "subscribe";
    String SUCCESS_ALERT = "//div[@class='alert-success alert']";

}
