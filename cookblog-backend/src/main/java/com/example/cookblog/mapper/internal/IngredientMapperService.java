package com.example.cookblog.mapper.internal;

import com.example.cookblog.dto.resources.IngredientResource;
import com.example.cookblog.mapper.IngredientMapper;
import com.example.cookblog.mapper.QuantityMapper;
import com.example.cookblog.model.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class IngredientMapperService implements IngredientMapper {

    private final QuantityMapper quantityMapper;

    @Override
    public IngredientResource mapIngredientToIngredientResource(Ingredient ingredient) {
        final var quantity = quantityMapper.mapQuantityToQuantityResource(ingredient.getQuantity());
        return IngredientResource.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .quantity(quantity)
                .build();
    }

    @Override
    public Ingredient mapIngredientResourceToIngredient(IngredientResource ingredientResource) {
        final var quantity = quantityMapper.mapQuantityResourceToQuantity(ingredientResource.quantity());
        return Ingredient.builder()
                .name(ingredientResource.name())
                .quantity(quantity)
                .build();
    }

}
