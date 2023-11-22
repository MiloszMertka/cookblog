Feature: Set recipe category
  As a user, I want to set recipe category
  So that I can discover and view different recipes on the blog

  Scenario: Set recipe category
    Given there are existing recipes in the blog with title "oldRecipe"
    And I prepared new category
    And I prepared update to change the category of the recipe
    When I updated recipe with the new information
    Then I should see the recipe category