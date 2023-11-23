Feature: Add information about the calorific value to a recipe
  As a user, I want to add information about the calorific value to a recipe
  so that I know how many calories I will consume when I eat it

  Scenario: Add the information about the calorific value to a recipe
    Given there are existing recipes in the blog with title "recipeWithIngredient"
    And I prepared update of the calorific value
    When I updated recipe with the new information
    Then I should see the recipe with the new information about the calorific value