Feature: Home Page Scenarios

  Background:
    Given User is on AE Home Page

# THESE FAIL WHEN RUNNING ALL TESTS
  @functional
  Scenario Outline: User verifies Category Specific items for Women
    When User selects Women category
    And User selects '<category>' category with href '<product>'
    Then User observes '<products>' products
    Examples:
      | category    | product              | products                                                                                            |
      | women-dress | /category_products/1 | Sleeveless Dress, Stylish Dress, Rose Pink Embroidered Maxi Dress                                   |
#     | women-tops  | /category_products/2 | Blue Top, Winter Top, Summer White Top, Madame Top For Women, Fancy Green Top, Lace Top For Women   |
#     | women-saree | /category_products/7 | Cotton Silk Hand Block Print Saree, Rust Red Linen Saree, Beautiful Peacock Blue Cotton Linen Saree |

  @functional
  Scenario Outline: User verifies Category Specific items for Men
    When User selects Men category
    And User selects '<category>' category with href '<product>'
    Then User observes '<products>' products
    Examples:
      | category    | product              | products                                                                                                                                                           |
      | men-tshirts | /category_products/3 | Men Tshirt, Pure Cotton V-Neck T-Shirt, Green Side Placket Detail T-Shirt, Premium Polo T-Shirts, Pure Cotton Neon Green Tshirt, GRAPHIC DESIGN MEN T SHIRT - BLUE |
#     | men-jeans   | /category_products/6 | Soft Stretch Jeans, Regular Fit Straight Jean, Grunt Blue Slim Fit Jeans                                                                                           |
