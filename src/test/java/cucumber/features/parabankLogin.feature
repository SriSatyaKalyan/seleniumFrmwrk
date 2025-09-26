Feature: ParaBank Login Feature

  Background:
    Given User is on Customer Login Page

    @smoke @regression @functional
    Scenario: Verify Customer observes Customer Login page elements
      Given User clicks on Index button
      Then User lands on Index page
      When User clicks on AboutUs button
      Then User lands on AboutUs page
      When User clicks on Contact button
      Then User lands on Contact page

    @smoke @functional
    Scenario Outline: Verify Customer can login with valid credentials
      Given User provides creds '<username>' and '<password>'
      When User click on Log In button
      Then User observes "internal error" message
      Examples:
        | username | password   |
        | johnd    | johndoe&*( |