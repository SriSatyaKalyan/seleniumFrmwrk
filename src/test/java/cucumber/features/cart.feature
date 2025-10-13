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

  @testing
  Scenario Outline: User adds multiple products to Cart and verifies Cart
    When User adds '<products>' to cart
    And User clicks on Cart option
    Then User lands on Cart page
    Then User observes Cart contains '<products>'
    Examples:
      | products             |
      | Blue Top, Winter Top |