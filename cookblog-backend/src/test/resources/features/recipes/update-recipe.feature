Feature: Update a recipe
  As a blog owner, I want to update a recipe
  So that I could correct it

  Scenario: Update a recipe
    Given I prepared update data
    When I update a recipe
    Then Recipe should be updated
