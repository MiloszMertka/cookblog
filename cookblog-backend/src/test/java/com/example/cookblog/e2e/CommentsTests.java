package com.example.cookblog.e2e;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(OrderAnnotation.class)
public class CommentsTests {

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
    void givenCommentData_whenCreateComment_thenCreatesNewComment() throws Exception {
        final var commentName = "Test name";
        final var menuButton = driver.findElement(By.cssSelector("button[aria-label='menu']"));
        menuButton.click();
        Thread.sleep(1000);
        final var createCommentButton = driver.findElement(By.xpath("//*[contains(text(),'Create comment')]"));
        createCommentButton.click();
        final var nameInput = driver.findElement(By.cssSelector("input[data-test-id='name']"));
        nameInput.sendKeys(commentName);
        final var saveButton = driver.findElement(By.cssSelector("button[aria-label='save']"));
        saveButton.click();
        Thread.sleep(1000);
        driver.navigate().to(APP_URL);
        Thread.sleep(1000);
        final var menuButtonAfterReload = driver.findElement(By.cssSelector("button[aria-label='menu']"));
        menuButtonAfterReload.click();
        Thread.sleep(1000);
        final var commentElement = driver.findElement(By.xpath("//*[contains(text(),'" + commentName + "')]"));
        assertThat(commentElement.isDisplayed()).isTrue();

        //  TODO Dodawanie komentarzy
    }

    @Test
    @Order(2)
    void givenSomeComment_whenDeleteComment_thenCommentIsRemoved() throws Exception {
        final var commentName = "Test name";
        final var menuButton = driver.findElement(By.cssSelector("button[aria-label='menu']"));
        menuButton.click();
        Thread.sleep(1000);
        final var commentToDelete = driver.findElement(By.xpath("//*[contains(text(),'" + commentName + "')]"));
        final var commentToDeleteParent = commentToDelete.findElement(By.xpath("./.."));
        final var deleteButton = commentToDeleteParent.findElement(By.cssSelector("button[aria-label='delete comment']"));
        deleteButton.click();
        Thread.sleep(1000);
        final var js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('[data-test-id=\"confirm-button\"]').click();");
        Thread.sleep(1000);
        assertThat(driver.findElements(By.xpath("//*[contains(text(),'" + commentName + "')]")).isEmpty()).isTrue();

        //  TODO Usuwanie komentarzy
    }

}
