package com.example.cookblog.seeder;

import com.example.cookblog.model.*;
import com.example.cookblog.repository.CategoryRepository;
import com.example.cookblog.repository.RecipeRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
class RecipeSeeder implements Seeder {

    private static final String SAMPLE_IMAGE_URL = "https://images.unsplash.com/photo-1476224203421-9ac39bcb3327?q=80&w=3870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final Faker faker;
    private final Set<Ingredient> generatedIngredients = new HashSet<>();

    @Override
    public void seed(int objectsToSeed) {
        final Set<Recipe> recipes = new HashSet<>();
        while (recipes.size() < objectsToSeed) {
            final var recipe = createRecipe();
            if (!recipes.contains(recipe)) {
                recipeRepository.save(recipe);
                recipes.add(recipe);
            }
        }
    }

    private Recipe createRecipe() {
        final var ingredients = createIngredients(5);
        final var image = createImage();
        final var comments = createComments(5);
        final var category = getRandomCategory();
        return Recipe.builder()
                .title(faker.lorem().sentence())
                .description(faker.lorem().paragraph())
                .ingredients(ingredients)
                .instructions(faker.lorem().paragraph(20))
                .image(image)
                .preparationTimeInMinutes(faker.number().numberBetween(20, 120))
                .portions(faker.number().numberBetween(1, 12))
                .calorificValue(faker.number().numberBetween(200, 1000))
                .comments(comments)
                .category(category)
                .build();
    }

    private Set<Ingredient> createIngredients(int ingredientsToCreate) {
        final Set<Ingredient> ingredients = new HashSet<>();
        while (ingredients.size() < ingredientsToCreate) {
            final var ingredient = createIngredient();
            if (!generatedIngredients.contains(ingredient)) {
                ingredients.add(ingredient);
                generatedIngredients.add(ingredient);
            }
        }
        return ingredients;
    }

    private Ingredient createIngredient() {
        final var quantity = createQuantity();
        return Ingredient.builder()
                .name(faker.lorem().word())
                .quantity(quantity)
                .build();
    }

    private Quantity createQuantity() {
        final var unit = getRandomUnit();
        return Quantity.builder()
                .amount(faker.number().numberBetween(1, 5))
                .unit(unit)
                .build();
    }

    private Unit getRandomUnit() {
        final var units = Arrays.asList(Unit.values());
        final var unitsCount = units.size();
        return units.get(faker.number().numberBetween(0, unitsCount - 1));
    }

    private Image createImage() {
        return Image.builder()
                .path(SAMPLE_IMAGE_URL)
                .build();
    }

    private List<Comment> createComments(int commentsToCreate) {
        final List<Comment> comments = new ArrayList<>();
        while (comments.size() < commentsToCreate) {
            final var comment = createComment();
            if (!comments.contains(comment)) {
                comments.add(comment);
            }
        }
        return comments;
    }

    private Comment createComment() {
        final var author = faker.name().firstName() + " " + faker.name().lastName();
        return Comment.builder()
                .author(author)
                .content(faker.lorem().sentence())
                .build();
    }

    private Category getRandomCategory() {
        final var categories = categoryRepository.findAll();
        final var categoriesCount = categories.size();
        return categories.get(faker.number().numberBetween(0, categoriesCount - 1));
    }

}
