Feature: Login Feature

  Background:
    Given User is on Login Page

  @smoke  @regression
  @Functional
  Scenario Outline: Verify User can provide details with Forgot Password
    Given User logs in using '<username>' and '<password>'
    When User clicks on Forgot Password link
    When User provides the details for '<name>', '<email>', '<phoneNumber>'
    When User clicks on Reset Login button
#    Then User observes '<tempPasswordMessage>' message
    Then User quits the browser
#    Then User receives '<tempPasswordMessage>' message
    Examples:
      | username                       | password | name | email            | phoneNumber | tempPasswordMessage                                          |
      | contact@rahulshettyacademy.com | password | John | user@example.com | 8889765432  | Please use temporary password 'rahulshettyacademy' to Login. |