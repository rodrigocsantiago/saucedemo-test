Feature: Products
  As a user
  I want to be able to check product details and filter the product list
  So that I can decide the best product to purchase

  Scenario Outline: Check product details
    Given I am logged into SauceDemo as a 'standard' user
    And A product with name '<Product Name>' and price '<Product Price>' exists
    When I click on '<Product Name>' product
    Then I should see the product details with name '<Product Name>' and price '<Product Price>'
    Examples:
      | Product Name        | Product Price |
      | Sauce Labs Backpack | 29.99         |
      | Sauce Labs Onesie   | 7.99          |


  Scenario: Filter product list by name
    Given I am logged into SauceDemo as a 'standard' user