Feature: Update exist category
  As a blog owner, I want to update exist category
  So that I could correct it

  Scenario: Update exist category
    Given There are existing categories in the blog with title "updateCategory"
    And I prepared update category data
    When I update category
    Then I should see updated category