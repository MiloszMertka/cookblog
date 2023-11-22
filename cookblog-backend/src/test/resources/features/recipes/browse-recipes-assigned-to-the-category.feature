Feature: Browse the list of recipes assigned to the category
  As a user, I want to browse the list of recipes
  So that I can discover and view different recipes on the blog

  Scenario: View a list of recipes assigned to the category
    Given there are existing recipes in the blog with title "recipe"
    When I request to get the category with its recipes by its ID
    Then I should receive the requested category with its recipes