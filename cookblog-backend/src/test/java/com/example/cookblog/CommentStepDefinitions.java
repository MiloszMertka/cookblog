package com.example.cookblog;

import com.example.cookblog.dto.requests.CommentRecipeRequest;
import com.example.cookblog.mapper.CommentMapper;
import com.example.cookblog.model.*;
import com.example.cookblog.repository.CategoryRepository;
import com.example.cookblog.repository.RecipeRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
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



import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentStepDefinitions {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Recipe recipe;
    private Comment comment;
    private Category category;
    private CommentRecipeRequest commentRecipeRequest;


    private ResultActions resultActions;

    @Before
    public void createRecipeAndCategory() {
        category = Category.builder()
                .name("category")
                .build();
        categoryRepository.deleteAll();
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
        comment = Comment.builder()
                .author("author")
                .content("content")
                .build();

        recipeRepository.deleteAll();
        recipeRepository.save(recipe);
    }

    @After
    public void cleanUp() {
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    // Feature: Create new comment
    @Given("I wrote a comment")
    public void iWroteAComment() throws Exception {
        commentRecipeRequest = CommentRecipeRequest.builder()
                .author(comment.getAuthor())
                .content(comment.getContent())
                .build();
    }

    @When("I post a comment")
    public void iPostAComment() throws Exception {
        final var content = objectMapper.writeValueAsString(commentRecipeRequest);
        resultActions = mockMvc.perform(post("/{id}/comment",
                    recipeRepository.findByTitle(recipe.getTitle()).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    @Then("I should be able to see it in comment section")
    public void iShouldBeAbleToSeeItInCommentSection() throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    // Feature: Delete my comment
    @Given("There is my comment")
    public void thereIsMyComment() throws Exception {
        final var repRecipe = recipeRepository.findByTitle(recipe.getTitle());
        repRecipe.getComments().add(comment);
        recipeRepository.save(repRecipe);
    }

    @When("I deleted a comment")
    public void iDeletedAComment() throws Exception {
        resultActions = mockMvc.perform(delete("{id}/comment/{commentId}",
                recipeRepository.findByTitle(recipe.getTitle()).getId()));
    }

    @Then("I should not be able to see it anymore")
    public void iShouldNotBeAbleToSeeItAnymore() throws Exception {
        resultActions = mockMvc.perform(get("/{id}",
                recipeRepository.findByTitle(
                        recipe.getTitle())
                        .getId()));
        resultActions.andExpect(status().isNotFound());
    }
}
