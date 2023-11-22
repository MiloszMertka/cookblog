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
import io.cucumber.java.After;
import io.cucumber.java.Before;
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

    private Recipe recipe;
    private Category category;
    private CreateRecipeRequest createRecipeRequest;
    private UpdateRecipeRequest updateRecipeRequest;
    private ResultActions resultActions;

    @Before
    public void createCategory() {
        category = Category.builder()
                .name("category")
                .build();
        categoryRepository.save(category);
    }

    @After
    public void deleteCategory() {
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    // Feature: Browse the list of recipes

    @Given("there are existing recipes in the blog with title {string}")
    public void thereAreExistingRecipesInTheBlog(String title) {
        recipe = Recipe.builder()
                .title(title)
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
        recipeRepository.save(recipe);
    }

    @When("I navigate to the list of recipes")
    public void iNavigateToTheListOfRecipes() throws Exception {
        resultActions = mockMvc.perform(get("/recipes/search/{query}", recipe.getTitle()));
    }

    @Then("I should see a list of available recipes")
    public void iShouldSeeAListOfAvailableRecipes() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value(recipe.getTitle()))
                .andExpect(jsonPath("$[0].description").value(recipe.getDescription()))
                .andExpect(jsonPath("$[0].instructions").value(recipe.getInstructions()))
                .andExpect(jsonPath("$[0].ingredients[0].name")
                        .value(recipe.getIngredients().iterator().next().getName()))
                .andExpect(jsonPath("$[0].ingredients[0].quantity.amount")
                        .value(recipe.getIngredients().iterator().next().getQuantity().getAmount()))
                .andExpect(jsonPath("$[0].ingredients[0].quantity.unit")
                        .value(recipe.getIngredients().iterator().next().getQuantity().getUnit().toString()))
                .andExpect(jsonPath("$[0].image.path").value(recipe.getImage().getPath()))
                .andExpect(jsonPath("$[0].preparationTimeInMinutes")
                        .value(recipe.getPreparationTimeInMinutes()))
                .andExpect(jsonPath("$[0].portions").value(recipe.getPortions()))
                .andExpect(jsonPath("$[0].calorificValue").value(recipe.getCalorificValue()))
                .andExpect(jsonPath("$[0].comments").isArray()
                );
    }

    // Feature: Create new recipe
    @Given("I prepared recipe data")
    public void iPreparedRecipeData() {
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
        resultActions = mockMvc.perform(get("/recipes/{id}",
                recipeRepository.findByTitle(createRecipeRequest.title()).orElseThrow().getId()));
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
    @When("I delete the recipe")
    public void iDeleteTheRecipeWithTitle() throws Exception {
        resultActions = mockMvc.perform(delete("/recipes/{id}", recipe.getId()))
                .andExpect(status().isNoContent());
    }

    @Then("the recipe should no longer exist")
    public void theRecipeShouldNoLongerExist() throws Exception {
        mockMvc.perform(get("/recipes/{id}", recipe.getId()))
                .andExpect(status().isNotFound());
    }

    // Feature: Get a recipe
    @When("I request to get the recipe by its ID {string}")
    public void iRequestToGetTheRecipeByItsID(String title) throws Exception {
        resultActions = mockMvc.perform(get("/recipes/{id}",
                recipeRepository.findByTitle(title).orElseThrow().getId()));
    }

    @Then("I should receive the details of the requested recipe")
    public void iShouldReceiveTheDetailsOfTheRequestedRecipe() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.title")
                                .value(recipe.getTitle()),
                        jsonPath("$.description")
                                .value(recipe.getDescription()),
                        jsonPath("$.ingredients[0].name")
                                .value(recipe.getIngredients().iterator().next().getName()),
                        jsonPath("$.ingredients[0].quantity.amount")
                                .value(recipe.getIngredients().iterator().next().getQuantity().getAmount()),
                        jsonPath("$.ingredients[0].quantity.unit")
                                .value(recipe.getIngredients().iterator().next().getQuantity().getUnit().toString()),
                        jsonPath("$.instructions")
                                .value(recipe.getInstructions()),
                        jsonPath("$.image.path")
                                .value(recipe.getImage().getPath())
                );
    }

    // Feature: Update a recipe
    @Given("I prepared update data")
    public void thereIsExistingRecipe() {
        updateRecipeRequest = UpdateRecipeRequest.builder()
                .title("Updated")
                .description("description")
                .ingredients(Set.of(IngredientResource.builder()
                        .name("ingredient")
                        .quantity(QuantityResource.builder()
                                .amount(1)
                                .unit(Unit.GRAM)
                                .build()
                        ).build()))
                .instructions("instructions")
                .image(ImageResource.builder()
                        .path("image-path")
                        .build())
                .category(CategoryResource.builder()
                        .id(null)
                        .name(category.getName())
                        .recipes(null)
                        .build())
                .build();
    }

    @When("I update a recipe")
    public void iUpdateARecipe() throws Exception {
        final var content = objectMapper.writeValueAsString(updateRecipeRequest);
        resultActions = mockMvc.perform(put("/recipes/{id}", recipe.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        recipe = recipeRepository.findById(recipe.getId()).orElse(null);
    }

    @Then("Recipe should be updated")
    public void recipeShouldBeUpdated() throws Exception {
        resultActions.andExpect(status().isNoContent());
    }


}
