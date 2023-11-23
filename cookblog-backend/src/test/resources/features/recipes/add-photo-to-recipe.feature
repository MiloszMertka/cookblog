Feature: Add photo to recipe
  As a blog owner, I want to add photo to my recipe
  So that other users can see how the dish looks like

Scenario: Add photo to recipe
  Given I prepared recipe data
  When I create new recipe
  And I get a recipe
  Then I should see photo for the dish