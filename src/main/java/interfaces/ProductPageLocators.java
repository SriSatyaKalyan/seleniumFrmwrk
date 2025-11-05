package interfaces;

public interface ProductPageLocators {

    String PRODUCT_ADD_TO_CART = "//button[@class='btn btn-default cart']";
    String ADD_TO_CART_NOTIFICATION = "//div[@class='modal-content']";
    String PRODUCT_DETAILS = "//a[contains(@href, '/product_details/')]";
    String PRODUCT_CHECKOUT_BUTTON = "//a[@class='btn btn-default check_out']";

    static String PRODUCT_ROW(String productName){
        return "//tr[.//td[@class='cart_description']//a[text()='" + productName + "']]";
    }
    String PRODUCT_ROW_QUANTITY_ELEMENT = ".//td[@class='cart_quantity']/button";

    static String PRODUCT_CONTAINER(String productContainer){
        return "//div[@class='product-image-wrapper'][.//p[text()='" + productContainer + "']]";
    }
    String PRODUCT_CONTAINER_ADD_TO_CART = ".//div[@class='productinfo text-center']//a[contains(@class,'add-to-cart')]";

    String ADD_TO_CART_NOTIFICATION_TEXT = "(//div[@class='modal-body']//p[@class='text-center'])[1]";
    String ADD_TO_CART_NOTIFICATION_MODAL_CLOSE = "//button[contains(@class, 'close-modal')]";
}