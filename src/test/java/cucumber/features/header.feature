Feature:  Header Scenarios

  Background:
    Given User is on AE Home Page

  @smoke @functional
  Scenario: User checks for elements in Header
      When User clicks on Products option
      Then User lands on Products page
      When User clicks on Cart option
      Then User lands on Cart page
      When User clicks on TestCases option
      Then User lands on TestCases page
      When User clicks on APITesting option
      Then User lands on APITesting page
      When User clicks on VideoTutorials option
      Then User lands on VideoTutorials page
      When User navigates to the previous page
      When User clicks on ContactUs option
      Then User lands on ContactUs page