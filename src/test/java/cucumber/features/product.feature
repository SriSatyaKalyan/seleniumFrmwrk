Feature: Automation Exercise Home Page Scenarios

  Background:
    Given User is on AE Home Page

  @smoke @functional
    @testing
  Scenario Outline: User verifies Product Details
    When User clicks on View Product for '<product>'
    Then User observes Product Details '<product>', '<category>', '<cost>', '<availability>', '<condition>' and '<brand>'
    Then User observes Review section
    Examples:
      | product    | category | cost | availability | condition | brand |
      | Men Tshirt | Men      | 400  | In Stock     | New       | H&M   |

#  Scenario Outline: User verifies Product Details
#    When User clicks on View Product for '<product>'
#    Then User observes Review section
#    Examples:
#      | product                     |
#      | Sleeves Printed Top - White |
