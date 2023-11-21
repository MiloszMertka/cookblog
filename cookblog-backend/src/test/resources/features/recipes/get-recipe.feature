Feature: Get a recipe
  As a blog user, I want to get a recipe
  So that I can cook

  Scenario: Get a recipe
    Given there is an existing recipe in the blog
    When I request to get the recipe by its ID
    Then I should receive the details of the requested recipe
