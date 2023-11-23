Feature: Delete my comment
  As a blog user, I want to delete comment
  So that I can remove misleading or not true information

Scenario: Delete my comment
  Given There is my comment
  When I deleted a comment
  Then I should not be able to see it anymore