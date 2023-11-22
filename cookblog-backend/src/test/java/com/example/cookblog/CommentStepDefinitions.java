package com.example.cookblog;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentStepDefinitions {

    // Feature: Create new comment
    @Given("I wrote a comment")
    public void iWroteAComment() throws Exception {
        // TODO
    }

    @Given("I posted a comment")
    @When("I post a comment")
    public void iPostAComment() throws Exception {
        // TODO
    }

    @Then("I should be able to see it in comment section")
    public void iShouldBeAbleToSeeItInCommentSection() throws Exception {
        // TODO
    }

    // Feature: Delete my comment
    @When("I deleted a comment")
    public void iDeletedAComment() throws Exception {
        // TODO
    }

    @Then("I should not be able to see it anymore")
    public void iShouldNotBeAbleToSeeItAnymore() throws Exception {
        // TODO
    }
}
