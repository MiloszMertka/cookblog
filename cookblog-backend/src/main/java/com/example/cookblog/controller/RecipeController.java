package com.example.cookblog.controller;

import com.example.cookblog.dto.requests.CommentRecipeRequest;
import com.example.cookblog.dto.requests.CreateRecipeRequest;
import com.example.cookblog.dto.requests.UpdateRecipeRequest;
import com.example.cookblog.dto.resources.CategoryResource;
import com.example.cookblog.dto.resources.RecipeResource;
import com.example.cookblog.service.RecipeUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
class RecipeController {

    private final RecipeUseCases recipeUseCases;

    @GetMapping
    public ResponseEntity<Page<RecipeResource>> getPageOfRecipes(Pageable pageable) {
        final var pageOfRecipes = recipeUseCases.getPageOfRecipes(pageable);
        return ResponseEntity.ok(pageOfRecipes);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<RecipeResource>> searchRecipes(@PathVariable String query) {
        final var foundRecipes = recipeUseCases.searchRecipes(query);
        return ResponseEntity.ok(foundRecipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResource> getRecipe(@PathVariable Long id) {
        final var recipe = recipeUseCases.getRecipe(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/{id}/category")
    public ResponseEntity<CategoryResource> getRecipeCategory(@PathVariable Long id) {
        final var category = recipeUseCases.getRecipeCategory(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Void> createRecipe(@RequestBody @Valid CreateRecipeRequest createRecipeRequest) {
        recipeUseCases.createRecipe(createRecipeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id, @RequestBody @Valid UpdateRecipeRequest updateRecipeRequest) {
        recipeUseCases.updateRecipe(id, updateRecipeRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeUseCases.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> commentRecipe(@PathVariable Long id, @RequestBody @Valid CommentRecipeRequest commentRecipeRequest) {
        recipeUseCases.commentRecipe(id, commentRecipeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        recipeUseCases.deleteComment(id, commentId);
        return ResponseEntity.noContent().build();
    }

}
