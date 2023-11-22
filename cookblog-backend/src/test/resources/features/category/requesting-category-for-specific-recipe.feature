Feature: Request category for specific recipe
  As a blog user, I want to request category
  So that I can browse trough more related recipe to my need

Scenario: Request category for specific recipe
  Given There are existing recipe with category
  When I request a category
  Then I should see recipes only within that category