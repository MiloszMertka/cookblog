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
import org.springframework.test.web.servlet.ResultMatcher;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        deleteCategory();
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

    @When("I get a recipe")
    public void iGetARecipe() throws Exception {
        resultActions = mockMvc.perform(get("/recipes/{id}",
                recipeRepository.findByTitle(createRecipeRequest.title()).orElseThrow().getId()));
    }

    @Then("New recipe should be created")
    public void newRecipeShouldBeCreated() throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    // Feature: Search the recipe

    @When("I search for recipe")
    public void iSearchForRecipe() throws Exception {
        resultActions = mockMvc.perform(get("/recipes/search/{query}", recipe.getTitle()));
    }

    @Then("I should see requested recipe")
    public void iShouldSeeRequestedRecipe() throws Exception {
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(status().isOk())
                .andExpectAll(jsonPath("$[?(@.id != -1)]").exists(),
                        jsonListContentChecker("title",
                                recipe.getTitle()),
                        jsonListContentChecker("description",
                                recipe.getDescription()),
                        jsonListContentChecker("ingredients[0].name",
                                recipe.getIngredients().iterator().next().getName()),
                        jsonListContentChecker("ingredients[0].quantity.amount",
                                recipe.getIngredients().iterator().next().getQuantity().getAmount()),
                        jsonListContentChecker("ingredients[0].quantity.unit",
                                recipe.getIngredients().iterator().next().getQuantity().getUnit()),
                        jsonListContentChecker("instructions",
                                recipe.getInstructions()),
                        jsonListContentChecker("image.path",
                                recipe.getImage().getPath()));
    }

    // Feature: Add photo to recipe

    @Then("I should see photo for the dish")
    public void iShouldSeePhotoForTheDish() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.image.path")
                        .value(createRecipeRequest.image().path()));
    }

    // Feature: Create new category

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

    // Feature: Add the list of ingredients to recipe

    @And("I prepared list of ingredients data")
    public void iPreparedListOfIngredientsData() {
        updateRecipeRequest = UpdateRecipeRequest.builder()
                .title("recipe")
                .description("description")
                .ingredients(Set.of(IngredientResource.builder()
                        .name("newIngredient")
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

    @When("I updated recipe with the new list of ingredients")
    public void iUpdatedRecipeWithTheListOfIngredients() throws Exception {
        final var content = objectMapper.writeValueAsString(updateRecipeRequest);
        resultActions = mockMvc.perform(put("/recipes/{id}", recipe.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        recipe = recipeRepository.findById(recipe.getId()).orElse(null);
    }

    @Then("I should see the recipe with the new list of ingredients")
    public void iShouldSeeTheRecipeWithTheNewListOfIngredients() throws Exception {
        resultActions.andExpect(status().isNoContent());
        assertEquals(Objects.requireNonNull(recipe.getIngredients().stream().findFirst().orElse(null)).getName(), "newIngredient");
    }

    // Feature: Add information about the calorific value to a recipe

    @And("I prepared update of the calorific value")
    public void iPreparedUpdateOfTheCalorificValue() {
        updateRecipeRequest = UpdateRecipeRequest.builder()
                .title("recipe")
                .description("description")
                .ingredients(Set.of(IngredientResource.builder()
                        .name("newIngredient")
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
                .calorificValue(100)
                .build();
    }

    @When("I updated recipe with the new information")
    public void iUpdatedRecipeWithTheNewInformation() throws Exception {
        final var content = objectMapper.writeValueAsString(updateRecipeRequest);
        resultActions = mockMvc.perform(put("/recipes/{id}", recipe.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        recipe = recipeRepository.findById(recipe.getId()).orElse(null);
    }

    @Then("I should see the recipe with the new information about the calorific value")
    public void iShouldSeeTheRecipeWithTheNewInformationAboutTheCalorificValue() throws Exception {
        resultActions.andExpect(status().isNoContent());
        assertEquals(Objects.requireNonNull(recipe.getCalorificValue()), 100);
    }

    // Feature: Add information about the preparation time to a recipe

    @And("I prepared update of the preparation time")
    public void iPreparedUpdateOfThePreparationTime() {
        updateRecipeRequest = UpdateRecipeRequest.builder()
                .title("recipe")
                .description("description")
                .ingredients(Set.of(IngredientResource.builder()
                        .name("newIngredient")
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
                .preparationTimeInMinutes(10)
                .build();
    }

    @Then("I should see the recipe with the new information about the preparation time")
    public void iShouldSeeTheRecipeWithTheNewInformationAboutThePreparationTime() throws Exception {
        resultActions.andExpect(status().isNoContent());
        assertEquals(Objects.requireNonNull(recipe.getPreparationTimeInMinutes()), 10);
    }

    // Feature: Add information about portions to a recipe

    @And("I prepared update of portions")
    public void iPreparedUpdateOfPortions() {
        updateRecipeRequest = UpdateRecipeRequest.builder()
                .title("recipe")
                .description("description")
                .ingredients(Set.of(IngredientResource.builder()
                        .name("newIngredient")
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
                .portions(4)
                .build();
    }

    @Then("I should see the recipe with the new information about portions")
    public void iShouldSeeTheRecipeWithTheNewInformationAboutPortions() throws Exception {
        resultActions.andExpect(status().isNoContent());
        assertEquals(Objects.requireNonNull(recipe.getPortions()), 4);
    }

    // Feature: Set recipe category

    @And("I prepared new category")
    public void iPreparedNewCategory() {
        category = Category.builder()
                .name("newCategory")
                .build();
        categoryRepository.save(category);
    }

    @And("I prepared update to change the category of the recipe")
    public void iPreparedUpdateToChangeTheCategoryOfTheRecipe() {
        updateRecipeRequest = UpdateRecipeRequest.builder()
                .title("recipe")
                .description("description")
                .ingredients(Set.of(IngredientResource.builder()
                        .name("newIngredient")
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
                .preparationTimeInMinutes(10)
                .build();
    }

    @Then("I should see the recipe category")
    public void iShouldSeeTheRecipeCategory() throws Exception {
        resultActions.andExpect(status().isNoContent());
        assertEquals(Objects.requireNonNull(recipe.getCategory().getName()), category.getName());
    }

    // Feature: Browse recipes assigned to the category

    @When("I request to get the category with its recipes by its ID")
    public void iRequestToGetTheCategoryWithItsRecipesByItsID() throws Exception {
        category = categoryRepository.findById(category.getId()).orElse(null);
        assert category != null;
        resultActions = mockMvc.perform(get("/categories/{id}", category.getId()));
    }

    @Then("I should receive the requested category with its recipes")
    public void iShouldReceiveTheRequestedCategoryWithItsRecipes() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.name")
                                .value(category.getName()),
                        jsonPath("$.recipes[0].id").exists(),
                        jsonPath("$.recipes[0].title")
                                .value(category.getRecipes().get(0).getTitle()),
                        jsonPath("$.recipes[0].description")
                                .value(category.getRecipes().get(0).getDescription())
                );
    }

    private ResultMatcher jsonListContentChecker(String attributeName, Object expectedValue) {
        return jsonPath("$[?(@." + attributeName + " == '" + expectedValue + "')]").exists();
    }
}
