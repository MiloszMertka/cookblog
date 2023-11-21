Feature: Delete a recipe
  As a blog owner, I want to delete an existing recipe
  So that I can manage the content on my blog

  Scenario: Delete a recipe
    Given there are existing recipes in the blog with title "deleteTest"
    When I navigate to the list of recipes
    And I delete the recipe
    Then the recipe should no longer exist
