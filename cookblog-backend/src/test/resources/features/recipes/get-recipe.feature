Feature: Get a recipe
  As a blog user, I want to get a recipe
  So that I can cook

  Scenario: Get a recipe
    Given there are existing recipes in the blog with title "getARecipe"
    When I request to get the recipe by its ID "getARecipe"
    Then I should receive the details of the requested recipe
