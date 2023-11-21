Feature: Create new recipe
  As a blog owner, I want to create a new recipe
  So that I share it with my audience

  Scenario: Create new recipe
    Given I prepared recipe data
    When I create new recipe
    And I get all recipes
    Then I should see created recipe
