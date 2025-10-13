Feature: Automation Exercise Home Page Scenarios

  Background:
    Given User is on AE Home Page

  @smoke @functional
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

  @smoke @functional
  Scenario Outline: User searches for Product and verifies Product details
    When User clicks on Products option
    Then User lands on Products page
    When User searches for '<product>'
    And User clicks on View Product for '<product>'
    Then User observes Product Details '<product>', '<category>', '<cost>', '<availability>', '<condition>' and '<brand>'
    Examples:
      | product              | category | cost | availability | condition | brand              |
      | Men Tshirt           | Men      | 400  | In Stock     | New       | H&M                |
      | Lace Top For Women   | Women    | 1400 | In Stock     | New       | Mast & Harbour     |
      | Frozen Tops For Kids | Kids     | 278  | In Stock     | New       | Allen Solly Junior |

