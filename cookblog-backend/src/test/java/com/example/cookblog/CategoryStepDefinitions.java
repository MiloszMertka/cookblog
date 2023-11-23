package com.example.cookblog;

import com.example.cookblog.model.*;
import com.example.cookblog.repository.CategoryRepository;
import com.example.cookblog.repository.RecipeRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
@ActiveProfiles("test")
public class CategoryStepDefinitions {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    private Long recipeId;
    private Recipe recipe;
    private Category category;
    private ResultActions resultActions;

    @Before
    public void setUp(){
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @After
    public void cleanUp() {
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Given("I prepared category data")
    public void iPreparedCategoryData() {
        category = Category.builder()
                .name("category")
                .build();
    }

    @When("I create new category")
    public void iCreateNewCategory() throws Exception {
        final var content = objectMapper.writeValueAsString(category);
        resultActions = mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    @Then("New category should be created")
    public void newCategoryShouldBeCreated() throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    @When("I get all categories")
    public void iGetAllCategories() throws Exception {
        resultActions = mockMvc.perform(get("/categories"));
    }


    @Then("I should see created category")
    public void iShouldSeeCreatedCategory() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(category.getName()));
    }

    // Feature: Request category for specific recipe
    @Given("The recipe with category exist")
    public void thereAreExistingRecipeWithCategory() throws Exception {
        setUp();
        category = Category.builder()
            .name("category")
            .build();

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

        categoryRepository.save(category);
        recipeRepository.save(recipe);
    }

    @When("I request a category of that recipe")
    public void iRequestACategory() throws Exception {
        recipeId = recipeRepository.findByTitle(recipe.getTitle()).getId();
        resultActions = mockMvc.perform(get("/recipes/{id}/category", recipeId));
    }

    @Then("I should get category of that recipe")
    public void iShouldSeeRecipesOnlyWithinThatCategory() throws Exception {
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.name")
                .value(recipe.getCategory().getName()));
    }
}
