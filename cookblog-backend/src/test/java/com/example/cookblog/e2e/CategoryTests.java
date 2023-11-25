package com.example.cookblog.e2e;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void givenSomeCategory_whenGetCategory_thenCategoryIsGot() throws Exception {
        final var categoryName = "Test name";
        final var menuButton = driver.findElement(By.cssSelector("button[aria-label='menu']"));
        menuButton.click();
        Thread.sleep(1000);
        final var categoryLink = driver.findElement(By.xpath("//*[contains(text(),'" + categoryName + "')]"));
        categoryLink.click();
        Thread.sleep(1000);
        final var url = driver.getCurrentUrl();
        assertTrue(url.contains("/categories"));
    }

    @Test
    @Order(3)
    void givenSomeCategory_whenDeleteCategory_thenCategoryIsUpdated() throws Exception {
        final var categoryName = "Test name";
        final var menuButton = driver.findElement(By.cssSelector("button[aria-label='menu']"));
        menuButton.click();
        Thread.sleep(1000);
        final var updateCategoryName = "Updated test name";
        final var editCategoryElement = driver.findElement(By.xpath("//*[contains(text(),'" + categoryName + "')]"));
        final var editCategoryParentElement = editCategoryElement.findElement(By.xpath(".."));
        final var editCategoryLink = editCategoryParentElement.findElement(By.xpath(".//a[@aria-label='edit category']"));
        editCategoryLink.click();
        Thread.sleep(1000);
        final var nameInput = driver.findElement(By.cssSelector("input[data-test-id='name']"));
        nameInput.clear();
        nameInput.sendKeys(updateCategoryName);
        final var saveButton = driver.findElement(By.cssSelector("button[aria-label='save']"));
        saveButton.click();
        Thread.sleep(1000);
        driver.navigate().to(APP_URL);
        Thread.sleep(1000);
        final var menuButtonAfterReload = driver.findElement(By.cssSelector("button[aria-label='menu']"));
        menuButtonAfterReload.click();
        Thread.sleep(1000);
        final var categoryElement = driver.findElement(By.xpath("//*[contains(text(),'" + updateCategoryName + "')]"));
        assertThat(categoryElement.isDisplayed()).isTrue();
    }

    @Test
    @Order(4)
    void givenSomeCategory_whenDeleteCategory_thenCategoryIsRemoved() throws Exception {
        final var categoryName = "Updated test name";
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
