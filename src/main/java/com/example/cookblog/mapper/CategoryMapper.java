package com.example.cookblog.mapper;

import com.example.cookblog.dto.requests.CreateCategoryRequest;
import com.example.cookblog.dto.requests.UpdateCategoryRequest;
import com.example.cookblog.dto.resources.CategoryResource;
import com.example.cookblog.model.Category;

public interface CategoryMapper {

    CategoryResource mapCategoryToCategoryResource(Category category);

    CategoryResource mapCategoryWithRecipesToCategoryResource(Category category);

    Category mapCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    void updateCategoryFromUpdateCategoryRequest(Category category, UpdateCategoryRequest updateCategoryRequest);

    Category mapCategoryResourceToCategory(CategoryResource categoryResource);

}
