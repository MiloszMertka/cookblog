package com.example.cookblog.mapper.internal;

import com.example.cookblog.dto.requests.CreateRecipeRequest;
import com.example.cookblog.dto.requests.UpdateRecipeRequest;
import com.example.cookblog.dto.resources.RecipeResource;
import com.example.cookblog.mapper.*;
import com.example.cookblog.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RecipeMapperService implements RecipeMapper {

    private final IngredientMapper ingredientMapper;
    private final ImageMapper imageMapper;
    private final CommentMapper commentMapper;
    private CategoryMapper categoryMapper;

    @Lazy
    @Autowired
    private void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public RecipeResource mapRecipeToRecipeResource(Recipe recipe) {
        final var ingredients = recipe.getIngredients().stream()
                .map(ingredientMapper::mapIngredientToIngredientResource)
                .toList();
        final var image = imageMapper.mapImageToImageResource(recipe.getImage());
        final var comments = recipe.getComments().stream()
                .map(commentMapper::mapCommentToCommentResource)
                .toList();
        final var category = categoryMapper.mapCategoryToCategoryResource(recipe.getCategory());
        return RecipeResource.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .ingredients(ingredients)
                .instructions(recipe.getInstructions())
                .image(image)
                .preparationTimeInMinutes(recipe.getPreparationTimeInMinutes())
                .portions(recipe.getPortions())
                .calorificValue(recipe.getCalorificValue())
                .comments(comments)
                .category(category)
                .build();
    }

    @Override
    public Recipe mapCreateRecipeRequestToRecipe(CreateRecipeRequest createRecipeRequest) {
        final var ingredients = createRecipeRequest.ingredients().stream()
                .map(ingredientMapper::mapIngredientResourceToIngredient)
                .toList();
        final var image = imageMapper.mapImageResourceToImage(createRecipeRequest.image());
        final var category = categoryMapper.mapCategoryResourceToCategory(createRecipeRequest.category());
        final var recipe = Recipe.builder()
                .title(createRecipeRequest.title())
                .description(createRecipeRequest.description())
                .instructions(createRecipeRequest.instructions())
                .image(image)
                .preparationTimeInMinutes(createRecipeRequest.preparationTimeInMinutes())
                .portions(createRecipeRequest.portions())
                .calorificValue(createRecipeRequest.calorificValue())
                .category(category)
                .build();
        recipe.getIngredients().addAll(ingredients);
        return recipe;
    }

    @Override
    public void updateRecipeFromUpdateRecipeRequest(Recipe recipe, UpdateRecipeRequest updateRecipeRequest) {
        final var ingredients = updateRecipeRequest.ingredients().stream()
                .map(ingredientMapper::mapIngredientResourceToIngredient)
                .toList();
        final var image = imageMapper.mapImageResourceToImage(updateRecipeRequest.image());
        final var category = categoryMapper.mapCategoryResourceToCategory(updateRecipeRequest.category());
        recipe.setTitle(updateRecipeRequest.title());
        recipe.setDescription(updateRecipeRequest.description());
        recipe.setInstructions(updateRecipeRequest.instructions());
        recipe.setImage(image);
        recipe.setPreparationTimeInMinutes(updateRecipeRequest.preparationTimeInMinutes());
        recipe.setPortions(updateRecipeRequest.portions());
        recipe.setCalorificValue(updateRecipeRequest.calorificValue());
        recipe.setCategory(category);
        recipe.getIngredients().clear();
        recipe.getIngredients().addAll(ingredients);
    }

}
