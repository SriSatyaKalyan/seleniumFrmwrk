Feature: Automation Exercise Login Scenarios
  Background:
    Given User is on AE Home Page

  @functional
  @smoke @regression
#   Scenario to check if data can be filled up in the SignUp form
  Scenario: Register New User
    Given User clicks on Login button
    When User enters registration details
    Then User clicks on SignUp button
    When User fills in Account Information
    And User fills in Address Information
    And User clicks on Create Account button
    Then User verifies account creation

  @functional
  @smoke @regression
#   Scenario to check if data can be filled up in the SignUp form
  Scenario: Existing User logs in
    Given User clicks on Login button
    When User enters account details and logs in
    Then User lands on Home Page

  @functional
  @smoke @regression
  Scenario: User deletes account
    Given User clicks on Login button
    When User enters account details and logs in
    Then User lands on Home Page
    When User enters account details and logs in
    Then User lands on Home Page





#      And User deletes account
#      Then User verifies account deletion


