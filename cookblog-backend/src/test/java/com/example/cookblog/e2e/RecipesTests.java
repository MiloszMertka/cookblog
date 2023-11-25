package com.example.cookblog.e2e;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(OrderAnnotation.class)
public class RecipesTests {

    private static final String APP_URL = "http://localhost:4200";
    private final WebDriver driver = new SeleniumConfig().getDriver();

    @BeforeEach
    void setUp() {
        driver.get(APP_URL);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    public void givenCategoryData_whenViewRecipes_shouldSeeRecipesAssignedToCategories() throws Exception {
        driver.get(APP_URL + "/categories/2");
        Thread.sleep(1000);
        final var matCard = driver.findElement(By.cssSelector("mat-card"));
        assertThat(matCard.isDisplayed()).isTrue();
    }

    @Test
    public void givenCategory_whenSetCategoryOfRecipe_shouldSuccessfullySet() throws Exception {
        driver.get(APP_URL + "/recipes/edit/1");
        Thread.sleep(1000);
        var categorySelect = driver.findElement(By.cssSelector("mat-select[data-test-id='category']"));
        categorySelect.click();
        var lastMatOption = driver.findElements(By.cssSelector("mat-option")).get(driver.findElements(By.cssSelector("mat-option")).size() - 1);
        lastMatOption.click();
        final var saveButton = driver.findElement(By.cssSelector("button[aria-label='save']"));
        saveButton.click();
        Thread.sleep(1000);
        driver.get(APP_URL + "/recipes/edit/1");
        Thread.sleep(1000);
        categorySelect = driver.findElement(By.cssSelector("mat-select[data-test-id='category']"));
        categorySelect.click();
        lastMatOption = driver.findElements(By.cssSelector("mat-option")).get(driver.findElements(By.cssSelector("mat-option")).size() - 1);
        assertThat(lastMatOption.getAttribute("aria-selected")).isEqualTo("true");
    }

    @Test
    public void givenRecipeData_whenAddInformationAboutCalorificValue_shouldSuccessfullyAdd() throws Exception {
        String calorificValue = "123";
        driver.get(APP_URL + "/recipes/edit/1");
        Thread.sleep(1000);
        var calorificValueInput = driver.findElement(By.cssSelector("input[data-test-id='calorificValue']"));
        calorificValueInput.clear();
        calorificValueInput.sendKeys(calorificValue);
        final var saveButton = driver.findElement(By.cssSelector("button[aria-label='save']"));
        saveButton.click();
        Thread.sleep(1000);
        driver.get(APP_URL + "/recipes/edit/1");
        Thread.sleep(1000);
        calorificValueInput = driver.findElement(By.cssSelector("input[data-test-id='calorificValue']"));
        assertThat(calorificValueInput.getAttribute("value")).isEqualTo(calorificValue);
    }

    @Test
    public void givenRecipeData_whenAddInformationAboutPreparationTime_shouldSuccessfullyAdd() throws Exception {
        String preparationTimeInMinutes = "30";
        driver.get(APP_URL + "/recipes/edit/1");
        Thread.sleep(1000);
        var preparationTimeInput = driver.findElement(By.cssSelector("input[data-test-id='preparationTimeInMinutes']"));
        preparationTimeInput.clear();
        preparationTimeInput.sendKeys(preparationTimeInMinutes);
        final var saveButton = driver.findElement(By.cssSelector("button[aria-label='save']"));
        saveButton.click();
        Thread.sleep(1000);
        driver.get(APP_URL + "/recipes/edit/1");
        Thread.sleep(1000);
        preparationTimeInput = driver.findElement(By.cssSelector("input[data-test-id='preparationTimeInMinutes']"));
        assertThat(preparationTimeInput.getAttribute("value")).isEqualTo(preparationTimeInMinutes);
    }

    @Test
    public void givenRecipeData_whenAddInformationAboutPortions_shouldSuccessfullyAdd() throws Exception {
        String portions = "4";
        driver.get(APP_URL + "/recipes/edit/1");
        Thread.sleep(1000);
        var portionsInput = driver.findElement(By.cssSelector("input[data-test-id='portions']"));
        portionsInput.clear();
        portionsInput.sendKeys(portions);
        final var saveButton = driver.findElement(By.cssSelector("button[aria-label='save']"));
        saveButton.click();
        Thread.sleep(1000);
        driver.get(APP_URL + "/recipes/edit/1");
        Thread.sleep(1000);
        portionsInput = driver.findElement(By.cssSelector("input[data-test-id='portions']"));
        assertThat(portionsInput.getAttribute("value")).isEqualTo(portions);
    }

    @Test
    void givenRecipeData_whenAddListOfIngredients_shouldSuccessfullyAdd() throws Exception {
        String ingredientName = "ingredientTestName";
        String amount = "99";
        driver.get(APP_URL + "/recipes/edit/1");
        Thread.sleep(1000);
        final var addIngredientButton = driver.findElement(By.xpath("//*[contains(text(),'Add ingredient')]"));
        addIngredientButton.click();
        Thread.sleep(1000);
        final var ingredientsNameInputs = driver.findElements(By.cssSelector("input[data-test-id='ingredient-name']"));
        final var newIngredientNameInput = ingredientsNameInputs.get(ingredientsNameInputs.size() - 1);
        newIngredientNameInput.sendKeys(ingredientName);
        final var ingredientsAmounts = driver.findElements(By.cssSelector("input[data-test-id='ingredient-amount']"));
        final var newIngredientAmount = ingredientsAmounts.get(ingredientsAmounts.size() - 1);
        newIngredientAmount.clear();
        newIngredientAmount.sendKeys(amount);
        final var saveButton = driver.findElement(By.cssSelector("button[aria-label='save']"));
        saveButton.click();
        Thread.sleep(1000);
        driver.get(APP_URL + "/recipes/edit/1");
        Thread.sleep(1000);
        final var ingredientNamesAfterSave = driver.findElements(By.cssSelector("input[data-test-id='ingredient-name']"));
        final var newIngredientNameAfterSave = ingredientNamesAfterSave.get(1);
        String resultIngredientName = newIngredientNameAfterSave.getAttribute("value");
        final var ingredientAmountsAfterSave = driver.findElements(By.cssSelector("input[data-test-id='ingredient-amount']"));
        final var newIngredientAmountsAfterSave = ingredientAmountsAfterSave.get(1);
        String resultIngredientAmount = newIngredientAmountsAfterSave.getAttribute("value");

        assertEquals(ingredientName, resultIngredientName);
        assertEquals(amount, resultIngredientAmount);
    }
}
