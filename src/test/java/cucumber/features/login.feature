Feature: Login Page Tests

  Background:
    Given User is on Login Page

  @smoke  @regression
  @Functional
  Scenario Outline: Verify User can provide details with Forgot Password
    Given User logs in using '<username>' and '<password>'
    Then User observes error on screen
    When User clicks on Forgot Password link
    When User provides the details for '<name>', '<email>', '<phoneNumber>'
    When User clicks on Reset Login button
    Then User observes temporary password message
    Examples:
      | username                       | password | name | email            | phoneNumber |
      | contact@rahulshettyacademy.com | password | John | user@example.com | 8889765432  |

  @smoke  @regression
  @Functional
  Scenario Outline: Verify User logs in providing valid credentials
    Given User logs in using '<username>' and '<password>'
    When User clicks on Sign In
    Then User lands on Log In page with '<welcomeMessage>'
#    Then User quits the browser
    Examples:
      | username                       | password           | welcomeMessage                  |
      | contact@rahulshettyacademy.com | rahulshettyacademy | You are successfully logged in. |