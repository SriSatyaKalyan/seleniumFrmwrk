Feature: Automation Exercise Footer Scenarios

  Background:
    Given User is on AE Home Page

  @functional
  Scenario Outline: User validates successful alert message in Footer
    When User observes Subscription option
    Then User enters '<emailId>' in subscriptionForm
    Then User observes alert '<message>'
    Examples: :
  | emailId | message |
  | johnnydoe@gmail.com | You have been successfully subscribed! |

  @smoke @functional
  Scenario Outline: User validates successful validation message in Footer
    When User observes Subscription option
    Then User enters '<emailId>' in subscriptionForm
    Then User observes validation '<message>'
    And User clicks on Cart option
    Then User lands on Cart page
    When User observes Subscription option
    Then User enters '<emailId>' in subscriptionForm
    Then User observes validation '<message>'
    Examples: :
  | emailId | message |
  | johnnydoegmail.com | Please include an \'@\' in the email address |