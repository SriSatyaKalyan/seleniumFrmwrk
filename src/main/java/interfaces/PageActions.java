package interfaces;

/**
 * Interface defining common page actions for polymorphism
 * Demonstrates: Abstraction and Polymorphism
 */
public interface PageActions {

    /**
     * Navigate to the page
     */
    void navigateToPage();

    /**
     * Verify page is loaded
     * @return true if page is loaded, false otherwise
     */
    boolean isPageLoaded();

    /**
     * Get page title
     * @return page title
     */
    String getPageTitle();

    /**
     * Wait for page to load completely
     */
    void waitForPageLoad();
}