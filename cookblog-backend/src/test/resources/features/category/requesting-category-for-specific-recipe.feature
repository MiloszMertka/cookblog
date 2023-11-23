Feature: Request category for specific recipe
  As a blog user, I want to request category
  So that I can browse trough more related recipe to my need

Scenario: Request category for specific recipe
  Given The recipe with category exist
  When I request a category of that recipe
  Then I should get category of that recipe