package com.example.cookblog.e2e;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(OrderAnnotation.class)
public class CategoryTests {

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
    @Order(1)
    @Disabled
    void givenCategoryData_whenCreateCategory_thenCreatesNewCategory() throws Exception {
        final var categoryName = "Test name";
        final var menuButton = driver.findElement(By.cssSelector("button[aria-label='menu']"));
        menuButton.click();
        Thread.sleep(1000);
        final var createCategoryButton = driver.findElement(By.xpath("//*[contains(text(),'Create category')]"));
        createCategoryButton.click();
        final var nameInput = driver.findElement(By.cssSelector("input[data-test-id='name']"));
        nameInput.sendKeys(categoryName);
        final var saveButton = driver.findElement(By.cssSelector("button[aria-label='save']"));
        saveButton.click();
        Thread.sleep(1000);
        driver.navigate().to(APP_URL);
        Thread.sleep(1000);
        final var menuButtonAfterReload = driver.findElement(By.cssSelector("button[aria-label='menu']"));
        menuButtonAfterReload.click();
        Thread.sleep(1000);
        final var categoryElement = driver.findElement(By.xpath("//*[contains(text(),'" + categoryName + "')]"));
        assertThat(categoryElement.isDisplayed()).isTrue();
    }

    @Test
    @Order(2)
    @Disabled
    void givenSomeCategory_whenDeleteCategory_thenCategoryIsRemoved() throws Exception {
        final var categoryName = "Test name";
        final var menuButton = driver.findElement(By.cssSelector("button[aria-label='menu']"));
        menuButton.click();
        Thread.sleep(1000);
        final var categoryToDelete = driver.findElement(By.xpath("//*[contains(text(),'" + categoryName + "')]"));
        final var categoryToDeleteParent = categoryToDelete.findElement(By.xpath("./.."));
        final var deleteButton = categoryToDeleteParent.findElement(By.cssSelector("button[aria-label='delete category']"));
        deleteButton.click();
        Thread.sleep(1000);
        final var js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('[data-test-id=\"confirm-button\"]').click();");
        Thread.sleep(1000);
        assertThat(driver.findElements(By.xpath("//*[contains(text(),'" + categoryName + "')]")).isEmpty()).isTrue();
    }

}
