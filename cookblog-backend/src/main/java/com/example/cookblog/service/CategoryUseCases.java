package com.example.cookblog.service;

import com.example.cookblog.dto.requests.CreateCategoryRequest;
import com.example.cookblog.dto.requests.UpdateCategoryRequest;
import com.example.cookblog.dto.resources.CategoryResource;

import java.util.List;

public interface CategoryUseCases {

    List<CategoryResource> getAllCategories();

    CategoryResource getCategoryWithItsRecipes(Long id);

    void createCategory(CreateCategoryRequest createCategoryRequest);

    void updateCategory(Long id, UpdateCategoryRequest updateCategoryRequest);

    void deleteCategory(Long id);

}
