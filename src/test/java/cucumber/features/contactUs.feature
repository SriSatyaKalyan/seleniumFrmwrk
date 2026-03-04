Feature: Contact Us Scenarios

  Background:
    Given User is on AE Home Page
    When User clicks on ContactUs option
    Then User lands on ContactUs page

  @functional
  Scenario Outline: User fills ContactUs form and submits
    When User enters GetInTouch details '<name>', '<email>', '<subject>' and '<message>'
    Then User validates submission of details
    Examples:
    | name        | email            | subject      | message                             |
    | Bob Tractor | bobtor@gmail.com | Appreciation | Hi. I think this website is amazing |