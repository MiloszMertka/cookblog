package com.example.cookblog;

import com.example.cookblog.dto.requests.CreateCategoryRequest;
import com.example.cookblog.dto.requests.UpdateCategoryRequest;
import com.example.cookblog.model.Category;
import com.example.cookblog.repository.CategoryRepository;
import io.cucumber.java.en.And;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private Category category;
    private UpdateCategoryRequest updateCategoryRequest;
    private ResultActions resultActions;

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

    @Given("There are existing categories in the blog with title {string}")
    public void thereAreExistingCategoriesInTheBlogWithTitle(String name) {
        category = Category.builder()
                .name(name)
                .build();
        categoryRepository.save(category);
    }

    @And("I prepared update category data")
    public void iPreparedUpdateCategoryData() {
        updateCategoryRequest = UpdateCategoryRequest
                .builder()
                .name("Updated")
                .build();
    }

    @When("I update category")
    public void iUpdateCategory() throws Exception {
        final var content = objectMapper.writeValueAsString(updateCategoryRequest);
        resultActions = mockMvc.perform(put("/categories/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        category = categoryRepository.findById(category.getId()).orElse(null);
    }


    @Then("I should see updated category")
    public void iShouldSeeUpdatedCategory() throws Exception {
        resultActions.andExpect(status().isNoContent());
        assertEquals(category.getName(), "Updated");
    }

    @When("I delete the category")
    public void iDeleteTheCategory() throws Exception {
        resultActions = mockMvc.perform(delete("/categories/{id}", category.getId()))
                .andExpect(status().isNoContent());
    }

    @Then("The category should no longer exist")
    public void theCategoryShouldNoLongerExist() throws Exception {
        mockMvc.perform(get("/categories/{id}", category.getId()))
                .andExpect(status().isNotFound());
    }

    @When("I request to get the category by its ID {string}")
    public void iRequestToGetTheCategoryByItsID(String name) throws Exception {
        resultActions = mockMvc.perform(get("/categories/{id}", category.getId()))
                .andExpect(status().isOk());
    }

    @Then("I should receive the details of the requested category")
    public void iShouldReceiveTheDetailsOfTheRequestedCategory() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.name")
                                .value(category.getName()));
    }
}
