Feature: Create new category
  As a blog owner, I want to create a new category
  So that I can categorize my recipes

  Scenario: Create new category
    Given I prepared category data
    When I create new category
    And I get all categories
    Then I should see created category
