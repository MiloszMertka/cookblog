package com.example.cookblog.mapper;

import com.example.cookblog.dto.requests.CreateRecipeRequest;
import com.example.cookblog.dto.requests.UpdateRecipeRequest;
import com.example.cookblog.dto.resources.RecipeResource;
import com.example.cookblog.model.Recipe;

public interface RecipeMapper {

    RecipeResource mapRecipeToRecipeResource(Recipe recipe);

    Recipe mapCreateRecipeRequestToRecipe(CreateRecipeRequest createRecipeRequest);

    void updateRecipeFromUpdateRecipeRequest(Recipe recipe, UpdateRecipeRequest updateRecipeRequest);

}
