Feature: Add the list of ingredients to recipe
  As a blog owner, I want to add a list of ingredients to a recipe
  so that I have an up-to-date collection of recipes

  Scenario: Add the list of ingredients to recipe
    Given there are existing recipes in the blog with title "recipeWithIngredient"
    And I prepared list of ingredients data
    When I updated recipe with the new list of ingredients
    Then I should see the recipe with the new list of ingredients