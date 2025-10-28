package interfaces;

public interface HomePageLocators {
    String WOMEN_CATEGORY_XPATH = "//a[@href='#Women']";
    String MEN_CATEGORY_XPATH = "//a[@href='#Men']";
    static String GENDER_SPECIFIC_CATEGORY_XPATH(String href) {
        return "//a[@href='" + href + "']";
    }
    String PRODUCTS_LIST = "//div[@class='single-products']//div[@class='productinfo text-center']/p";

}
