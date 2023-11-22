Feature: Delete exist category
  As a blog owner, I want to delete exist category
  So that I can manage the content on my blog

  Scenario: Delete exist category
    Given There are existing categories in the blog with title "theCategory"
    When I delete the category
    Then The category should no longer exist