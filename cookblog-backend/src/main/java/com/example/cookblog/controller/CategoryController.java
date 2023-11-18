package com.example.cookblog.controller;

import com.example.cookblog.dto.requests.CreateCategoryRequest;
import com.example.cookblog.dto.requests.UpdateCategoryRequest;
import com.example.cookblog.dto.resources.CategoryResource;
import com.example.cookblog.service.CategoryUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
class CategoryController {

    private final CategoryUseCases categoryUseCases;

    @GetMapping
    public ResponseEntity<List<CategoryResource>> getAllCategories() {
        final var categories = categoryUseCases.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResource> getCategoryWithItsRecipes(@PathVariable Long id) {
        final var category = categoryUseCases.getCategoryWithItsRecipes(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
        categoryUseCases.createCategory(createCategoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id, @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest) {
        categoryUseCases.updateCategory(id, updateCategoryRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryUseCases.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
