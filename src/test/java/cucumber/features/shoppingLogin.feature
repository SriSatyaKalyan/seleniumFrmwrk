Feature: Automation Exercise Login Scenarios

  Background:
    Given User is on AE Home Page

  @end-end
  Scenario: Register New User
    Given User clicks on Login option
    When User enters registration details
    Then User clicks on SignUp button
    When User fills in Account Information
    And User fills in Address Information
    And User clicks on Create Account button
    Then User verifies account creation

  @functional
  @smoke @regression
  Scenario: Existing User logs in
    Given User clicks on Login option
    When User enters account details
    And User clicks on Login button
    Then User lands on Home Page
    When User clicks on Logout button
    Then User is on SignUp-Login Page

#  @end-end
#  Scenario: User deletes account
#    Given User clicks on Login option
#    When User enters account details
#    And User clicks on Login button
#    When User lands on Home Page
#    And User deletes account
#    Then User verifies account deletion

  @functional
  @smoke @regression
  Scenario Outline: New User logs in and fails
    Given User clicks on Login option
    When User enters invalid creds '<emailaddress>' and '<password>'
    And User clicks on Login button
    Then User observes '<errorMessage>'
    Examples:
      | emailaddress        | password  | errorMessage |
      | johnnydoe@gmail.com | j%hnDu$k! | Your email or password is incorrect! |