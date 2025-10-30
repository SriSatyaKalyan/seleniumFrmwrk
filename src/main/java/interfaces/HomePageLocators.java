package interfaces;

public interface HomePageLocators {

    //header
    String HEADER_SECTION = "//ul[@class='nav navbar-nav']";
    String HEADER_PRODUCTS = "//a[@href='/products']";

    String WOMEN_CATEGORY_XPATH = "//a[@href='#Women']";
    String MEN_CATEGORY_XPATH = "//a[@href='#Men']";
    String HEADER_CART = "//a[@href='/view_cart']";
    String HEADER_TESTCASES = "//a[@href='/test_cases']";
    String HEADER_APILIST = "//a[@href='/api_list']";
    String HEADER_CONTACTUS = "//a[@href='/contact_us']";
    String HEADER_VIDEOTUTORIALS = "//a[@href='https://www.youtube.com/c/AutomationExercise']";

    static String GENDER_SPECIFIC_CATEGORY_XPATH(String href) {
        return "//a[@href='" + href + "']";
    }
    String PRODUCTS_LIST = "//div[@class='single-products']//div[@class='productinfo text-center']/p";

}
