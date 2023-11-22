Feature: Add information about portions to a recipe
  As a user, I want to add information about portions to a recipe
  so that I know for how many people the recipe is

  Scenario: Add the information about portions to a recipe
    Given there are existing recipes in the blog with title "recipeWithIngredient"
    And I prepared update of portions
    When I updated recipe with the new information
    Then I should see the recipe with the new information about portions