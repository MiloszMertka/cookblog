package com.example.cookblog;

import com.example.cookblog.dto.requests.CommentRecipeRequest;
import com.example.cookblog.model.*;
import com.example.cookblog.repository.CategoryRepository;
import com.example.cookblog.repository.RecipeRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentStepDefinitions {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Long recipeId;
    private Long commentId;
    private Recipe recipe;
    private Comment comment;
    private Category category;
    private CommentRecipeRequest commentRecipeRequest;


    private ResultActions resultActions;

    @Before
    public void setUp() {
        cleanUp();
        category = Category.builder()
                .name("category")
                .build();
        categoryRepository.save(category);

        recipe = Recipe.builder()
                .title("title")
                .description("description")
                .instructions("instructions")
                .ingredients(Set.of(Ingredient.builder()
                        .name("ingredient")
                        .quantity(Quantity.builder()
                                .amount(1)
                                .unit(Unit.GRAM)
                                .build())
                        .build()))
                .image(Image.builder()
                        .path("image-path")
                        .build())
                .category(category)
                .build();
    }

    @After
    public void cleanUp() {
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    // Feature: Create new comment
    @Given("I wrote a comment")
    public void iWroteAComment() throws Exception {
        setUp();
        comment = Comment.builder()
                .author("author")
                .content("content")
                .build();
    }

    @And("There is a recipe to comment on")
    public void thereIsARecipeToCommentOn() throws Exception {
        recipeRepository.save(recipe);
        recipeId = recipeRepository.findByTitle(recipe.getTitle()).orElseThrow().getId();
    }

    @When("I post a comment")
    public void iPostAComment() throws Exception {
        final var content = objectMapper.writeValueAsString(comment);
        resultActions = mockMvc.perform(post("/recipes/{id}/comment", recipeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    @Then("I should be able to see it in comment section")
    public void iShouldBeAbleToSeeItInCommentSection() throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    // Feature: Delete my comment
    @Given("There is a commented recipe")
    public void thereIsMyComment() throws Exception {
        setUp();
        recipeRepository.save(recipe);
        comment = Comment.builder()
                .author("author")
                .content("content")
                .build();
        final var response = recipeRepository.findByTitle(recipe.getTitle()).orElseThrow();
        response.getComments().add(comment);
        recipeRepository.save(response);
    }

    @When("I deleted a comment")
    public void iDeletedAComment() throws Exception {
        recipeId = recipeRepository.findByTitle(recipe.getTitle()).orElseThrow().getId();
        commentId = recipeRepository.findById(recipeId).get()
                .getComments().stream().findAny().get().getId();

        resultActions = mockMvc.perform(delete("/recipes/{id}/comment/{commentId}",
                recipeId, commentId));

    }

    @Then("I should not be able to see it anymore")
    public void iShouldNotBeAbleToSeeItAnymore() throws Exception {
        resultActions.andExpect(status().isNoContent());
        final var commentExists = recipeRepository.findById(recipeId).get()
                .getComments().stream()
                .anyMatch(com -> Objects.equals(com.getId(), commentId));
        assertFalse(commentExists);
    }
}
