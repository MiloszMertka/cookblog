package com.example.cookblog.dto.requests;

import com.example.cookblog.dto.resources.CategoryResource;
import com.example.cookblog.dto.resources.ImageResource;
import com.example.cookblog.dto.resources.IngredientResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Builder
@Jacksonized
public record CreateRecipeRequest(
        @NotBlank @Size(max = 255) String title,
        @NotBlank @Size(max = 2000) String description,
        @NotNull Set<@NotNull @Valid IngredientResource> ingredients,
        @NotBlank @Size(max = 5000) String instructions,
        @NotNull @Valid ImageResource image,
        @Positive Integer preparationTimeInMinutes,
        @Positive Integer portions,
        @Positive Integer calorificValue,
        @NotNull @Valid CategoryResource category) {

}