package com.example.cookblog.mapper.internal;

import com.example.cookblog.dto.requests.CreateCategoryRequest;
import com.example.cookblog.dto.requests.UpdateCategoryRequest;
import com.example.cookblog.dto.resources.CategoryResource;
import com.example.cookblog.mapper.CategoryMapper;
import com.example.cookblog.mapper.RecipeMapper;
import com.example.cookblog.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CategoryMapperService implements CategoryMapper {

    private final RecipeMapper recipeMapper;

    @Override
    public CategoryResource mapCategoryToCategoryResource(Category category) {
        return CategoryResource.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public CategoryResource mapCategoryWithRecipesToCategoryResource(Category category) {
        final var recipes = category.getRecipes().stream()
                .map(recipeMapper::mapRecipeToRecipeResource)
                .toList();
        return CategoryResource.builder()
                .id(category.getId())
                .name(category.getName())
                .recipes(recipes)
                .build();
    }

    @Override
    public Category mapCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest) {
        return Category.builder()
                .name(createCategoryRequest.name())
                .build();
    }

    @Override
    public void updateCategoryFromUpdateCategoryRequest(Category category, UpdateCategoryRequest updateCategoryRequest) {
        category.setName(updateCategoryRequest.name());
    }

    @Override
    public Category mapCategoryResourceToCategory(CategoryResource categoryResource) {
        return Category.builder()
                .name(categoryResource.name())
                .build();
    }

}
