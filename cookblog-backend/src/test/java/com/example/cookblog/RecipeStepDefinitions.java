package com.example.cookblog;

import com.example.cookblog.dto.requests.CreateRecipeRequest;
import com.example.cookblog.dto.resources.CategoryResource;
import com.example.cookblog.dto.resources.ImageResource;
import com.example.cookblog.dto.resources.IngredientResource;
import com.example.cookblog.dto.resources.QuantityResource;
import com.example.cookblog.model.Category;
import com.example.cookblog.model.Unit;
import com.example.cookblog.repository.CategoryRepository;
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
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private CreateRecipeRequest createRecipeRequest;
    private ResultActions resultActions;

    @Given("I prepared recipe data")
    public void iPreparedRecipeData() throws JsonProcessingException {
        Category category = Category.builder()
                .name("category1")
                .build();
        categoryRepository.save(category);
        createRecipeRequest = CreateRecipeRequest.builder()
                .title("title")
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
                        .path("path")
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
        System.out.println(objectMapper.writeValueAsString(createRecipeRequest));
    }

    @When("I create new recipe")
    public void iCreateNewRecipe() throws Exception {
        final var content = objectMapper.writeValueAsString(createRecipeRequest);
        resultActions = mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
    }

    @Then("New recipe should be created")
    public void newRecipeShouldBeCreated() throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    @When("I get all recipes")
    public void iGetAllRecipes() throws Exception {
        resultActions = mockMvc.perform(get("/recipes/{id}", 1L));
    }

    @Then("I should see created recipe")
    public void iShouldSeeCreatedRecipe() throws Exception {
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();

        resultActions.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.title").value(createRecipeRequest.title()),
                        jsonPath("$.description").value(createRecipeRequest.description()),
                        jsonPath("$.ingredients[0].name").value(createRecipeRequest.ingredients().iterator().next().name()),
                        jsonPath("$.ingredients[0].quantity.amount").value(createRecipeRequest.ingredients().iterator().next().quantity().amount()),
                        jsonPath("$.ingredients[0].quantity.unit").value(createRecipeRequest.ingredients().iterator().next().quantity().unit().toString()),
                        jsonPath("$.instructions").value(createRecipeRequest.instructions()),
                        jsonPath("$.image.path").value(createRecipeRequest.image().path()),
                        jsonPath("$.preparationTimeInMinutes").value(createRecipeRequest.preparationTimeInMinutes()),
                        jsonPath("$.portions").value(createRecipeRequest.portions()),
                        jsonPath("$.calorificValue").value(createRecipeRequest.calorificValue())
                );
    }


}
