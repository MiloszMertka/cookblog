Feature: Update a recipe
  As a blog owner, I want to update a recipe
  So that I could correct it

  Scenario: Update a recipe
    Given there are existing recipes in the blog with title "updateRecipe"
    And I prepared update data
    When I update a recipe
    And I request to get the recipe by its ID "Updated"
    Then I should receive the details of the requested recipe
