package com.example.cookblog.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeTests {
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
    public void givenRecipeData_WhenCreateRecipe_ThenSeeRecipe() throws InterruptedException {
        final var recipeTitle = "title";
        final var recipeDescription = "description";
        final var recipeInstructions = "instructions";
        final var preparationTimeInMinutes = "1";
        final var portions = "1";
        final var imagePath = "https://images.unsplash.com/photo-1476224203421-9ac39bcb3327?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
        final var ingredientName = "ingredient";
        final var calorificValue = "1";
        driver.findElement(By.cssSelector("a[aria-label='create recipe']")).click();

        driver.findElement(By.cssSelector("input[data-test-id='title']")).sendKeys(recipeTitle);
        driver.findElement(By.cssSelector("textarea[data-test-id='description']")).sendKeys(recipeDescription);
        driver.findElement(By.cssSelector("textarea[data-test-id='instructions']")).sendKeys(recipeInstructions);
        driver.findElement(By.cssSelector("input[data-test-id='image']")).sendKeys(imagePath);

        Thread.sleep(2000);
        final var categoryDropDown = driver.findElement(By.cssSelector("mat-select[data-test-id='category']"));
        categoryDropDown.click();
        Thread.sleep(1000);
        driver.findElement(By.id("mat-option-5")).click();

        driver.findElement(By.cssSelector("input[data-test-id='preparationTimeInMinutes']")).sendKeys(preparationTimeInMinutes);
        driver.findElement(By.cssSelector("input[data-test-id='portions']")).sendKeys(portions);
        driver.findElement(By.cssSelector("input[data-test-id='calorificValue']")).sendKeys(calorificValue);
        driver.findElement(By.cssSelector("input[data-test-id='ingredient-name']")).sendKeys(ingredientName);

        driver.findElement(By.cssSelector("button[aria-label='save']")).click();

        Thread.sleep(2000);


    }

}
