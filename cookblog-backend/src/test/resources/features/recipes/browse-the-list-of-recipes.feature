Feature: Browse the list of recipes
  As a user, I want to browse the list of recipes
  So that I can discover and view different recipes on the blog

  Scenario: View a list of recipes
    Given there are existing recipes in the blog
    When I navigate to the list of recipes
    Then I should see a list of available recipes