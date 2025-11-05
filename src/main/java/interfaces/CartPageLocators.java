package interfaces;

public interface CartPageLocators {

    static String PRODUCT_SPECIFIC_ROW(String product) {
        return "//tr[.//td[@class='cart_description']//a[text()='" + product + "']]";
    }
    String DELETE_PRODUCT = ".//a[@class='cart_quantity_delete']";
}
