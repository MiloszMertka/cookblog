package com.example.cookblog.mapper;

import com.example.cookblog.dto.resources.IngredientResource;
import com.example.cookblog.model.Ingredient;

public interface IngredientMapper {

    IngredientResource mapIngredientToIngredientResource(Ingredient ingredient);

    Ingredient mapIngredientResourceToIngredient(IngredientResource ingredientResource);

}
