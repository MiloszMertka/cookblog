Feature: Get a category
  As a blog user, I want to get a category
  So that I can see details of category

  Scenario: Get a exist category
    Given There are existing categories in the blog with title "getCategory"
    When I request to get the category by its ID "getCategory"
    Then I should receive the details of the requested category