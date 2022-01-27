Feature: Login
  As a user
  I want to log into the SauceDemo application
  So that I can access its functionalities

  Scenario Outline: Login as a regular user
    Given I am at the SauceDemo login page
    When I log into the application using a '<UserType>' user
    Then I should see the products page
    Examples:
      | UserType           |
      | standard           |
      | problem            |
      | performance glitch |

  Scenario: Attempt to login with a locked out user
    Given I am at the SauceDemo login page
    When I log into the application using a 'locked out' user
    Then I should see a message saying the user has been locked out