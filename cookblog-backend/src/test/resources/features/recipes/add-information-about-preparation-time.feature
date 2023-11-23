Feature: Add information about the preparation time to a recipe
  As a user, I want to add information about the preparation time to a recipe
  so that I know how long it will take to prepare the recipe

  Scenario: Add the information about the preparation time to a recipe
    Given there are existing recipes in the blog with title "recipeWithIngredient"
    And I prepared update of the preparation time
    When I updated recipe with the new information
    Then I should see the recipe with the new information about the preparation time