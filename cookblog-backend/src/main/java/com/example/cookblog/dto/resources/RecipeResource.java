package com.example.cookblog.dto.resources;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Jacksonized
public record RecipeResource(
        Long id,
        String title,
        String description,
        List<IngredientResource> ingredients,
        String instructions,
        ImageResource image,
        Integer preparationTimeInMinutes,
        Integer portions,
        Integer calorificValue,
        List<CommentResource> comments,
        @JsonBackReference CategoryResource category
) {

}