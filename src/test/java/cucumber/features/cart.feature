Feature: Automation Exercise Home Page Scenarios

  Background:
    Given User is on AE Products Page

  @smoke @functional
  Scenario Outline: User adds multiple items of same product and verifies Cart
    When User clicks on View Product for '<product>'
    And User clicks on Add To Cart '<number>' times
    And User clicks on Cart option
    Then User lands on Cart page
    Then User observes Cart contains '<product>' '<number>' times
    Examples:
      | product  | number |
      | Blue Top | 3      |

  @smoke @functional
  Scenario Outline: User adds multiple products to Cart and verifies Cart
    When User adds '<products>' to cart
    And User clicks on Cart option
    Then User lands on Cart page
    Then User observes Cart contains '<products>'
    Examples:
      | products             |
      | Blue Top, Winter Top |

  @smoke @functional
  Scenario Outline: User adds products to Cart, verifies addition and removes product from Cart
    When User adds '<products>' to cart
    And User clicks on Cart option
    Then User lands on Cart page
    Then User observes Cart contains '<products>'
    When User removes '<toBeRemovedProduct>' from Cart
    Then User observes Cart contains '<productsLeft>'
    Examples:
      | products             | toBeRemovedProduct | productsLeft |
      | Blue Top, Winter Top | Blue Top           | Winter Top   |

  @smoke @functional
  Scenario Outline: User logs in during Cart Checkout
    When User adds '<products>' to cart
    And User clicks on Cart option
    Then User lands on Cart page
    Then User observes Cart contains '<products>'
    When User clicks on Proceed To Checkout
    And User clicks on Register on Checkout Alert
    When User enters valid credentials '<email>' and '<password>'
    And User clicks on Login button
    And User clicks on Cart option
    And User clicks on Proceed To Checkout
    When User verifies delivery address on Checkout Page with '<name>', '<address>', '<country>' and '<phone>'
    Examples:
      | products             | email            | password  | name           | address                 | country       | phone      |
      | Blue Top, Winter Top | jdough@gmail.com | j%hnD*ug! | Mr. John Dough | Dough Imports & Exports | United States | 9798998888 |

  @smoke @functional @testing
  Scenario Outline: User registers during Cart Checkout
    When User adds '<products>' to cart
    And User clicks on Cart option
    Then User lands on Cart page
    Then User observes Cart contains '<products>'
    When User clicks on Proceed To Checkout
    And User clicks on Register on Checkout Alert
    When User enters registration details with '<name>' and '<emailAddress>'
    Then User clicks on SignUp button
    When User fills in Account Information with '<name>', '<emailAddress>', '<password>', '<dobDay>', '<dobMonth>', '<dobYear>', '<newsletterSelect>'
    And User fills in Address Information with '<firstName>', '<lastName>', '<company>', '<address1>', '<address2>', '<state>', '<city>', '<zipcode>' and '<mobileNumber>'
    And User clicks on Create Account button
    Then User verifies account creation
    And User clicks on Cart option
    And User clicks on Proceed To Checkout
    When User verifies delivery address on Checkout Page with '<name>', '<address>', '<country>' and '<mobileNumber>'
#    And User enters comment '<comment>' and places order
    And User enters the following comment and places order:
    """
    These are amazing products.
    Can't wait to try them.
    Looking forward to more purchases!
    """
    And User enters payment information
    And User confirms order placement
    And User deletes account
    Then User verifies account deletion
    Examples:
      | products             | name      | emailAddress       | password | dobDay | dobMonth | dobYear | newsletterSelect | firstName | lastName | company                | address                | address1       | address2      | state      | city     | zipcode | country       | mobileNumber | comment                                             |
      | Blue Top, Winter Top | Mary Jain | maryjain@gmail.com | M@ryJ@!n | 10     | 5        | 2001    | false            | Mary      | Jain     | Mary Imports & Exports | Mary Imports & Exports | 123 Happy Lane | Suite No: 456 | California | Westwood | 90009   | United States | 9798998888   | These are amazing products. Can't wait to try them. |