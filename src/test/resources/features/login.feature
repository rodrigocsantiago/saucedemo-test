Feature: Login
  As a user
  I want to log into the SauceDemo application
  So that I can access its functionalities

  Scenario: Login as a standard user
    Given I am at the SauceDemo login page
    When I log into the application using a 'standard' user
    Then I should see the products page

  Scenario: Attempt to login with a locked out user
    Given I am at the SauceDemo login page
    When I log into the application using a 'locked out' user
    Then I should see a message saying the user has been locked out

  Scenario: Login as a problem user
    Given I am at the SauceDemo login page
    When I log into the application using a 'problem' user
    Then I should see the products page

  Scenario: Login as a user with performance glitch
    Given I am at the SauceDemo login page
    When I log into the application using a 'performance glitch' user
    Then I should see the products page