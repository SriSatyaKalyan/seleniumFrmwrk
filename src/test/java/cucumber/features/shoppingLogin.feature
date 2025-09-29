Feature: Automation Exercise Login Scenarios
  Background:
    Given User is on AE Home Page

    @functional
    @smoke @regression
#   Scenario to check if data can be filled up in the SignUp form
    Scenario: User registers as new user
      Given User clicks on Login button
      When User enters registration details
      Then User clicks on SignUp button
      When User fills in Account Information
      And User fills in Address Information
