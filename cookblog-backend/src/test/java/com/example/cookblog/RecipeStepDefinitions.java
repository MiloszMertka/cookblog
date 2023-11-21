package com.example.cookblog;

import com.example.cookblog.dto.requests.CreateRecipeRequest;
import com.example.cookblog.dto.requests.UpdateRecipeRequest;
import com.example.cookblog.dto.resources.CategoryResource;
import com.example.cookblog.dto.resources.ImageResource;
import com.example.cookblog.dto.resources.IngredientResource;
import com.example.cookblog.dto.resources.QuantityResource;
import com.example.cookblog.model.*;
import com.example.cookblog.repository.CategoryRepository;
import com.example.cookblog.repository.RecipeRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
public class RecipeStepDefinitions {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    private CreateRecipeRequest createRecipeRequest;
    private ResultActions resultActions;


    // Feature: Browse the list of recipes

    @Given("there are existing recipes in the blog")
    public void thereAreExistingRecipesInTheBlog() {
        Category category1 = Category.builder()
                .name("Feature: Browse the list of recipes")
                .build();
        categoryRepository.save(category1);
        Recipe recipe = Recipe.builder()
                .title("Feature: Browse the list of recipes")
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
                .category(category1)
                .build();
        recipeRepository.save(recipe);
    }

    @When("I navigate to the list of recipes")
    public void iNavigateToTheListOfRecipes() throws Exception {
        resultActions = mockMvc.perform(get("/recipes/search/{query}", "Feature: Browse the list of recipes"));
    }

    @Then("I should see a list of available recipes")
    public void iShouldSeeAListOfAvailableRecipes() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$[0].id").value(1L), // Replace 1L with the expected ID
                        jsonPath("$[0].title").value("Feature: Browse the list of recipes"),
                        jsonPath("$[0].description").value("description"),
                        jsonPath("$[0].instructions").value("instructions")
                );
    }

    // Feature: Create new recipe
    @Given("I prepared recipe data")
    public void iPreparedRecipeData() {
        Category category = Category.builder()
                .name("Feature: Create new recipe")
                .build();
        categoryRepository.save(category);
        createRecipeRequest = CreateRecipeRequest.builder()
                .title("Feature: Create new recipe")
                .description("description")
                .ingredients(Set.of(IngredientResource.builder()
                        .name("name")
                        .quantity(QuantityResource.builder()
                                .amount(1)
                                .unit(Unit.GRAM)
                                .build()
                        ).build()))
                .instructions("instructions")
                .image(ImageResource.builder()
                        .path("image-path")
                        .build())
                .preparationTimeInMinutes(1)
                .portions(1)
                .calorificValue(1)
                .category(CategoryResource.builder()
                        .id(null)
                        .name(category.getName())
                        .recipes(null)
                        .build())
                .build();
    }

    @When("I create new recipe")
    public void iCreateNewRecipe() throws Exception {
        final var content = objectMapper.writeValueAsString(createRecipeRequest);
        resultActions = mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    @Then("New recipe should be created")
    public void newRecipeShouldBeCreated() throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    @When("I get a recipe")
    public void iGetARecipe() throws Exception {
        resultActions = mockMvc.perform(get("/recipes/{id}", 2L));
    }

    @Then("I should see created recipe")
    public void iShouldSeeCreatedRecipe() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.title")
                                .value(createRecipeRequest.title()),
                        jsonPath("$.description")
                                .value(createRecipeRequest.description()),
                        jsonPath("$.ingredients[0].name")
                                .value(createRecipeRequest.ingredients().iterator().next().name()),
                        jsonPath("$.ingredients[0].quantity.amount")
                                .value(createRecipeRequest.ingredients().iterator().next().quantity().amount()),
                        jsonPath("$.ingredients[0].quantity.unit")
                                .value(createRecipeRequest.ingredients().iterator().next().quantity().unit().toString()),
                        jsonPath("$.instructions")
                                .value(createRecipeRequest.instructions()),
                        jsonPath("$.image.path")
                                .value(createRecipeRequest.image().path()),
                        jsonPath("$.preparationTimeInMinutes")
                                .value(createRecipeRequest.preparationTimeInMinutes()),
                        jsonPath("$.portions")
                                .value(createRecipeRequest.portions()),
                        jsonPath("$.calorificValue")
                                .value(createRecipeRequest.calorificValue())
                );
    }


    // Feature: Delete a recipe
    @And("I delete the recipe")
    public void iDeleteTheRecipeWithTitle() throws Exception {
        resultActions = mockMvc.perform(delete("/recipes/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Then("the recipe should no longer exist")
    public void theRecipeShouldNoLongerExist() throws Exception {
        mockMvc.perform(get("/recipes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    // Feature: Get a recipe
    @Given("there is an existing recipe in the blog")
    public void thereIsAnExistingRecipeInTheBlog() {
    }

    @When("I request to get the recipe by its ID")
    public void iRequestToGetTheRecipeByItsID() throws Exception {
        resultActions = mockMvc.perform(get("/recipes/{id}", 2L));
    }

    @Then("I should receive the details of the requested recipe")
    public void iShouldReceiveTheDetailsOfTheRequestedRecipe() throws Exception {
        
    }
    // Feature: Update a recipe

    private UpdateRecipeRequest updateRecipeRequest;

    @Given("I prepared update data")
    public void thereIsExistingRecipe() {
        updateRecipeRequest = UpdateRecipeRequest.builder()
                .title("Updated")
                .description("description")
                .ingredients(Set.of(IngredientResource.builder()
                        .name("name")
                        .quantity(QuantityResource.builder()
                                .amount(1)
                                .unit(Unit.GRAM)
                                .build()
                        ).build()))
                .instructions("instructions")
                .image(ImageResource.builder()
                        .path("image-path")
                        .build())
                .preparationTimeInMinutes(1)
                .portions(1)
                .calorificValue(1)
                .category(CategoryResource.builder()
                        .id(null)
                        .name("Feature: Create new recipe")
                        .recipes(null)
                        .build())
                .build();
    }

    @When("I update a recipe")
    public void iUpdateARecipe() throws Exception {
        final var content = objectMapper.writeValueAsString(updateRecipeRequest);
        resultActions = mockMvc.perform(put("/recipes/{id}", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    @Then("Recipe should be updated")
    public void iShouldSeeUpdatedRecipe() throws Exception {
        resultActions.andExpect(status().isNoContent());
    }


}
