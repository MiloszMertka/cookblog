package com.example.cookblog.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

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


    private void clickNextUntilDisabled() throws InterruptedException {
        final var js = (JavascriptExecutor) driver;

        while (true) {
            var nextPageButton = driver.findElement(By.id("nextPageButton"));
            Thread.sleep(1000);
            if (nextPageButton.getAttribute("disabled") != null && nextPageButton.getAttribute("disabled").equals("true")) {
                Thread.sleep(1000);
                break;
            }
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", nextPageButton);
        }
    }

    private void clickNextPage() throws InterruptedException {
        final var js = (JavascriptExecutor) driver;

        var nextPageButton = driver.findElement(By.id("nextPageButton"));
        Thread.sleep(1000);

        if (nextPageButton.getAttribute("disabled") == null || !nextPageButton.getAttribute("disabled").equals("true")) {
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", nextPageButton);
        }
    }


    @Test
    @Order(1)
    public void givenRecipeData_WhenCreateRecipe_ThenSeeRecipe() throws InterruptedException {
        final var recipeTitle = "mytitle";
        final var recipeDescription = "mydescription";
        final var recipeInstructions = "myinstructions";
        final var preparationTimeInMinutes = "1";
        final var portions = "2";
        final var imagePath = "https://images.unsplash.com/photo-1476224203421-9ac39bcb3327?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
        final var ingredientName = "myingredient";
        final var calorificValue = "3";
        driver.findElement(By.cssSelector("a[aria-label='create recipe']")).click();

        driver.findElement(By.cssSelector("input[data-test-id='title']")).sendKeys(recipeTitle);
        driver.findElement(By.cssSelector("textarea[data-test-id='description']")).sendKeys(recipeDescription);
        driver.findElement(By.cssSelector("textarea[data-test-id='instructions']")).sendKeys(recipeInstructions);
        driver.findElement(By.cssSelector("input[data-test-id='image']")).sendKeys(imagePath);

        Thread.sleep(1000);
        final var categoryDropDown = driver.findElement(By.cssSelector("mat-select[data-test-id='category']"));
        categoryDropDown.click();
        Thread.sleep(1000);
        driver.findElement(By.id("mat-option-5")).click();

        driver.findElement(By.cssSelector("input[data-test-id='preparationTimeInMinutes']")).sendKeys(preparationTimeInMinutes);
        driver.findElement(By.cssSelector("input[data-test-id='portions']")).sendKeys(portions);
        driver.findElement(By.cssSelector("input[data-test-id='calorificValue']")).sendKeys(calorificValue);
        driver.findElement(By.cssSelector("input[data-test-id='ingredient-name']")).sendKeys(ingredientName);

        driver.findElement(By.cssSelector("button[aria-label='save']")).click();
        Thread.sleep(1000);
        clickNextUntilDisabled();

        final var titleElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeTitle + "')]"));
        assertThat(titleElement.isDisplayed()).isTrue();
        final var recipeDescriptionElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeDescription + "')]"));
        assertThat(recipeDescriptionElement.isDisplayed()).isTrue();
    }

    @Test
    @Order(2)
    public void givenRecipe_WhenRead_ThenShouldRead() throws InterruptedException {
        final var recipeTitle = "mytitle";
        final var recipeDescription = "mydescription";
        final var recipeInstructions = "myinstructions";
        final var preparationTimeInMinutes = "1";
        final var portions = "2";
        final var imagePath = "https://images.unsplash.com/photo-1476224203421-9ac39bcb3327?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
        final var ingredientName = "myingredient";
        final var calorificValue = "3";
        driver.navigate().to(APP_URL);
        clickNextUntilDisabled();
        final var readButton = driver.findElement(By.cssSelector("a[aria-label='readRecipeButton']"));
        readButton.click();
        Thread.sleep(1000);

        final var titleElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeTitle + "')]"));
        assertThat(titleElement.isDisplayed()).isTrue();
        final var recipeDescriptionElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeDescription + "')]"));
        assertThat(recipeDescriptionElement.isDisplayed()).isTrue();
        final var ingredientNameElement = driver.findElement(By.xpath("//*[contains(text(),'" + ingredientName + "')]"));
        assertThat(ingredientNameElement.isDisplayed()).isTrue();
        final var instructionsElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeInstructions + "')]"));
        assertThat(instructionsElement.isDisplayed()).isTrue();

        final var preparationTimeInMinutesElement = driver.findElement(By.xpath("//*[contains(text(),'" + preparationTimeInMinutes + " min." + "')]"));
        assertThat(preparationTimeInMinutesElement.isDisplayed()).isTrue();
        final var calorificValueElement = driver.findElement(By.xpath("//*[contains(text(),'" + calorificValue + " kcal" + "')]"));
        assertThat(calorificValueElement.isDisplayed()).isTrue();
        final var portionsElement = driver.findElement(By.xpath("//*[contains(normalize-space(),'" + portions + "')]"));
        assertThat(portionsElement.isDisplayed()).isTrue();
    }

    @Test
    @Order(3)
    public void givenRecipes_WhenBrowsing_ThenSeeRecipes() throws InterruptedException {
        final var recipeTitleFirstPage = "Dolores porro et maiores deserunt sit consequatur.";
        final var recipeDescriptionFirstPage = "Explicabo non et nam amet. Vitae laboriosam ipsa itaque. Et molestiae voluptatibus minima sint.";
        final var recipeTitleSecondPage = "Rerum porro tempora voluptas ipsum et facilis quibusdam.";
        final var recipeDescriptionSecondPage = "Omnis error aperiam. Veritatis id ullam sit numquam qui et nulla. Odio maxime adipisci velit. Vitae ut corrupti est provident. Aliquid non temporibus nihil dolores.";
        final var recipeTitleThirdPage = "Necessitatibus enim quasi animi facere ut quos.";
        final var recipeDescriptionThirdPage = "Temporibus nostrum ratione aut laborum molestiae quia quod. Qui fuga et laboriosam praesentium eum. Earum ad architecto hic fugit reiciendis. Beatae ad voluptas. Nostrum odit totam.";
        driver.navigate().to(APP_URL);
        Thread.sleep(1000);

        final var recipeTitleFirstPageElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeTitleFirstPage + "')]"));
        assertThat(recipeTitleFirstPageElement.isDisplayed()).isTrue();
        final var recipeDescriptionFirstPageElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeDescriptionFirstPage + "')]"));
        assertThat(recipeDescriptionFirstPageElement.isDisplayed()).isTrue();
        clickNextPage();
        Thread.sleep(1000);

        final var recipeTitleSecondPageElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeTitleSecondPage + "')]"));
        assertThat(recipeTitleSecondPageElement.isDisplayed()).isTrue();
        final var recipeDescriptionSecondPageElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeDescriptionSecondPage + "')]"));
        assertThat(recipeDescriptionSecondPageElement.isDisplayed()).isTrue();
        clickNextPage();
        Thread.sleep(1000);

        final var recipeTitleThirdPageElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeTitleThirdPage + "')]"));
        assertThat(recipeTitleThirdPageElement.isDisplayed()).isTrue();
        Thread.sleep(1000);

        final var recipeDescriptionThirdPageElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeDescriptionThirdPage + "')]"));
        Thread.sleep(1000);
        assertThat(recipeDescriptionThirdPageElement.isDisplayed()).isTrue();

    }

    @Test
    @Order(4)
    public void givenRecipe_WhenEdit_ThenSeeEditedRecipe() throws InterruptedException {
        final var recipeTitleEdited = "Editedmytitle";
        final var recipeDescriptionEdited = "Editedmydescription";
        final var recipeInstructionsEdited = "Editedmyinstructions";
        final var preparationTimeInMinutesEdited = "91";
        final var portionsEdited = "82";
        final var imagePathEdited = "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
        final var calorificValueEdited = "73";
        driver.navigate().to(APP_URL);
        Thread.sleep(1000);
        clickNextUntilDisabled();
        Thread.sleep(1000);
        final var editButton = driver.findElement(By.cssSelector("a[aria-label='editRecipeButton']"));
        editButton.click();

        final var titleInput = driver.findElement(By.cssSelector("input[data-test-id='title']"));
        titleInput.clear();
        titleInput.sendKeys(recipeTitleEdited);
        Thread.sleep(500);


        final var descriptionInput = driver.findElement(By.cssSelector("textarea[data-test-id='description']"));
        descriptionInput.clear();
        descriptionInput.sendKeys(recipeDescriptionEdited);
        Thread.sleep(500);

        final var instructionsInput = driver.findElement(By.cssSelector("textarea[data-test-id='instructions']"));
        instructionsInput.clear();
        instructionsInput.sendKeys(recipeInstructionsEdited);
        Thread.sleep(500);

        final var imageInput = driver.findElement(By.cssSelector("input[data-test-id='image']"));
        imageInput.clear();
        imageInput.sendKeys(imagePathEdited);
        Thread.sleep(500);

        final var preparationTimeInput = driver.findElement(By.cssSelector("input[data-test-id='preparationTimeInMinutes']"));
        preparationTimeInput.clear();
        preparationTimeInput.sendKeys(preparationTimeInMinutesEdited);
        Thread.sleep(500);

        final var portionsInput = driver.findElement(By.cssSelector("input[data-test-id='portions']"));
        portionsInput.clear();
        portionsInput.sendKeys(portionsEdited);
        Thread.sleep(500);

        final var calorificValueInput = driver.findElement(By.cssSelector("input[data-test-id='calorificValue']"));
        calorificValueInput.clear();
        calorificValueInput.sendKeys(calorificValueEdited);

        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button[aria-label='save']")).click();
        Thread.sleep(1000);
        driver.navigate().to(APP_URL);
        clickNextUntilDisabled();
        Thread.sleep(1000);
        final var readButton = driver.findElement(By.cssSelector("a[aria-label='readRecipeButton']"));
        readButton.click();
        Thread.sleep(1000);

        final var titleElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeTitleEdited + "')]"));
        assertThat(titleElement.isDisplayed()).isTrue();
        final var recipeDescriptionElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeDescriptionEdited + "')]"));
        assertThat(recipeDescriptionElement.isDisplayed()).isTrue();

        final var instructionsElement = driver.findElement(By.xpath("//*[contains(text(),'" + recipeInstructionsEdited + "')]"));
        assertThat(instructionsElement.isDisplayed()).isTrue();

        final var preparationTimeInMinutesElement = driver.findElement(By.xpath("//*[contains(text(),'" + preparationTimeInMinutesEdited + " min." + "')]"));
        assertThat(preparationTimeInMinutesElement.isDisplayed()).isTrue();
        final var calorificValueElement = driver.findElement(By.xpath("//*[contains(text(),'" + calorificValueEdited + " kcal" + "')]"));
        assertThat(calorificValueElement.isDisplayed()).isTrue();
        final var portionsElement = driver.findElement(By.xpath("//*[contains(normalize-space(),'" + portionsEdited + "')]"));
        assertThat(portionsElement.isDisplayed()).isTrue();
        Thread.sleep(1000);

    }

    @Test
    @Order(5)
    public void givenRecipe_WhenDeleteRecipe_ThenRecipeIsRemoved() throws InterruptedException {
        driver.navigate().to(APP_URL);
        clickNextUntilDisabled();
        final var recipeTitle = "title";
        final var menuButton = driver.findElement(By.cssSelector("button[aria-label='deleteRecipeButton']"));
        menuButton.click();
        Thread.sleep(1000);
        final var js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('[data-test-id=\"confirm-button\"]').click();");
        Thread.sleep(1000);
        driver.navigate().to(APP_URL);
        clickNextUntilDisabled();
        String actualText = driver.findElement(By.xpath("//*[contains(text(),'" + recipeTitle + "')]")).getText().trim();
        assertThat(actualText.isEmpty()).isTrue();
    }
}
