package interfaces;

public interface CartPageLocators {

    String ALERT_LOGIN_BUTTON = "//div[@class='modal-content']//a[@href='/login']";
    String ADDRESS_BOX = "//ul[@class='address item box']";
    String ADDRESS_BOX_NAME = "//li[@class='address_firstname address_lastname']";
    String ADDRESS_BOX_ADDRESS = "//li[@class='address_address1 address_address2']";
    String ADDRESS_BOX_COUNTRY = "//li[@class='address_country_name']";
    String ADDRESS_BOX_PHONE = "//li[@class='address_phone']";

    String COMMENT_SECTION = "//textarea[@name='message']";
    String PLACE_ORDER_BUTTON = "//a[@href='/payment']";

    static String PRODUCT_SPECIFIC_ROW(String product) {
        return "//tr[.//td[@class='cart_description']//a[text()='" + product + "']]";
    }
    String DELETE_PRODUCT = ".//a[@class='cart_quantity_delete']";
}
