Feature: Create new comment
  As a blog user, I want to create a comment
  So that I can pass tha information about the recipe.

Scenario: Create new comment
  Given I wrote a comment
  When I post a comment
  Then I should be able to see it in comment section