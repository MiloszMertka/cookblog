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
    private final String comment = "new Comment";

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
        final var authorName = "Author";

        driver.get(APP_URL + "/recipes/1");

        Thread.sleep(1000);
        final var authorInput = driver.findElement(By.cssSelector("input[data-test-id='author']"));
        authorInput.sendKeys(authorName);
        final var contentInput = driver.findElement(By.cssSelector("textarea[data-test-id='content']"));
        contentInput.sendKeys(comment);

        final var saveButton = driver.findElement(By.xpath("//*[contains(text(),' Add comment ')]"));
        saveButton.click();
        Thread.sleep(1000);
        driver.navigate().to(APP_URL + "/recipes/1");
        Thread.sleep(1000);
        final var commentElement = driver.findElement(By.xpath("//*[contains(text(),'" + comment + "')]"));
        assertThat(commentElement.isDisplayed()).isTrue();
    }

    @Test
    @Order(2)
    void givenSomeComment_whenDeleteComment_thenCommentIsRemoved() throws Exception {
        driver.get(APP_URL + "/recipes/1");
        Thread.sleep(1000);

        final var commentToDelete = driver.findElement(By.xpath("//*[contains(text(),'" + comment + "')]"));
        final var commentToDeleteParent = commentToDelete.findElements(By.xpath("./../.."));
        commentToDeleteParent.forEach(obj -> obj.findElement(By.cssSelector("button[aria-label='delete comment']")).click());

        Thread.sleep(1000);
        driver.navigate().to(APP_URL + "/recipes/1");

        Thread.sleep(1000);
        assertThat(driver.findElements(By.xpath("//*[contains(text(),'" + comment + "')]")).isEmpty()).isTrue();
    }

}