package com.example.cookblog;

import com.example.cookblog.model.Category;
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

    private Category category;
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

}
