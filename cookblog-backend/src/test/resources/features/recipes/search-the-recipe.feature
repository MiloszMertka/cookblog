Feature: Search the recipe
  As a blog user, I want to search for recipe
  So that I can get one I am looking for

Scenario: Search the recipe
  Given there are existing recipes in the blog with title "searchRecipes"
  When I search for recipe
  Then I should see requested recipe