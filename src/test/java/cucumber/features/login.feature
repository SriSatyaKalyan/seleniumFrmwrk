Feature: Automation Exercise Login Scenarios

  Background:
    Given User is on AE Home Page

  @maintenance
#  Scenario Outline: Register New User
#    Given User clicks on Login option
#    When User enters signup details with '<name>' and '<emailAddress>'
#    Then User clicks on SignUp button
#    When User fills in Account Information with '<name>', '<emailAddress>', '<password>', '<dobDay>', '<dobMonth>', '<dobYear>', '<newsletterSelect>'
#    And User fills in Address Information with '<firstName>', '<lastName>', '<company>', '<address1>', '<address2>', '<state>', '<city>', '<zipcode>' and '<mobileNumber>'
#    And User clicks on Create Account button
#    Then User verifies account creation
#    Examples:
#      | name       | emailAddress     | password  | dobDay | dobMonth | dobYear | newsletterSelect | firstName | lastName | company                 | address1       | address2      | state      | city     | zipcode | mobileNumber |
#      | John Dough | jdough@gmail.com | j%hnD*ug! | 15     | 8        | 2000    | true             | John      | Dough    | Dough Imports & Exports | 123 Happy Lane | Suite No: 456 | California | Westwood | 90009   | 9798998888   |

  @smoke @regression @functional
  Scenario Outline: Existing User logs in
    Given User clicks on Login option
    When User enters credentials '<email>' and '<password>'
    And User clicks on Login button
    Then User lands on Home Page
    When User clicks on Logout button
    Then User is on SignUp-Login Page
    Examples:
      | email            | password  |
      | jdough@gmail.com | j%hnD*ug! |

  @maintenance
  Scenario Outline: User deletes account
    Given User clicks on Login option
    When User enters credentials '<email>' and '<password>'
    And User clicks on Login button
    When User lands on Home Page
    And User deletes account
    Then User verifies account deletion
    Examples:
      | email | password |
#      | jdough@gmail.com | j%hnD*ug! |
#      | maryjain@gmail.com | M@ryJ@!n |

  @regression @functional @API
  Scenario Outline: New User logs in and fails
    Given User clicks on Login option
    When User enters credentials '<email>' and '<password>'
    And User clicks on Login button
    Then User observes '<errorMessage>' message
    Then User checks verifyLogin endpoint using '<email>' and '<password>'
    Examples:
      | email               | password  | errorMessage                         |
      | johnnydoe@gmail.com | j%hnDu$k! | Your email or password is incorrect! |

  @smoke @API
  Scenario: User is created, updated and deleted via APIs
    Given User loads test data from JSON file "api-user-data.json"
    When User creates account via API using loaded data
    And User updates account via API using loaded data
    Then User deletes account via API using loaded data
