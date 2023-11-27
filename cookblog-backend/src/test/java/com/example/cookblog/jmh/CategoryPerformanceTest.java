package com.example.cookblog.jmh;

import com.example.cookblog.CookblogApplication;
import com.example.cookblog.dto.requests.*;
import com.example.cookblog.dto.resources.ImageResource;
import com.example.cookblog.dto.resources.IngredientResource;
import com.example.cookblog.dto.resources.QuantityResource;
import com.example.cookblog.mapper.CategoryMapper;
import com.example.cookblog.mapper.RecipeMapper;
import com.example.cookblog.model.*;
import com.example.cookblog.repository.CategoryRepository;
import com.example.cookblog.repository.RecipeRepository;
import com.example.cookblog.service.CategoryUseCases;
import com.example.cookblog.service.RecipeUseCases;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class CategoryPerformanceTest {

    private ConfigurableApplicationContext context;
    private CategoryUseCases categoryService;
    private RecipeUseCases recipeService;
    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private CategoryMapper categoryMapper;
    private RecipeMapper recipeMapper;

    private Recipe recipe;
    private Long recipeId;
    private Category category;
    private Long categoryId;

    @Test
    public void contextLoads() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CategoryPerformanceTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        this.context = new SpringApplication(CookblogApplication.class).run();

        categoryService = this.context.getBean(CategoryUseCases.class);
        recipeService = this.context.getBean(RecipeUseCases.class);
        categoryRepository = this.context.getBean(CategoryRepository.class);
        recipeRepository = this.context.getBean(RecipeRepository.class);
        categoryMapper = this.context.getBean(CategoryMapper.class);
        recipeMapper = this.context.getBean(RecipeMapper.class);

        category = Category.builder()
                .name("category" + System.currentTimeMillis())
                .build();
        categoryRepository.save(category);

        categoryId = categoryService.getAllCategories().get(0).id();

        recipe = Recipe.builder()
                .title("recipe" + System.currentTimeMillis())
                .description("description")
                .instructions("instructions")
                .ingredients(Set.of(Ingredient.builder()
                        .name("ingredient")
                        .quantity(Quantity.builder()
                                .amount(1)
                                .unit(Unit.GRAM)
                                .build())
                        .build()))
                .image(Image.builder()
                        .path("image-path")
                        .build())
                .category(category)
                .build();
        recipeRepository.save(recipe);

        recipeId = recipeRepository.findAll().get(0).getId();
    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void createCategoryBenchmark() {
        String uniqueCategoryName = "category" + System.currentTimeMillis();

        categoryService.createCategory(CreateCategoryRequest.builder()
                .name(uniqueCategoryName)
                .build());
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void createRecipeBenchmark() {
        String uniqueRecipeName = "recipe" + System.currentTimeMillis();

        recipeService.createRecipe(CreateRecipeRequest.builder()
                .title(uniqueRecipeName)
                .description("description")
                .instructions("instructions")
                .ingredients(Set.of(IngredientResource.builder()
                        .name("ingredient")
                        .quantity(QuantityResource.builder()
                                .amount(1)
                                .unit(Unit.GRAM)
                                .build())
                        .build()))
                .image(ImageResource.builder()
                        .path("image-path")
                        .build())
                .category(categoryMapper.mapCategoryToCategoryResource(categoryRepository.findById(category.getId()).orElse(null)))
                .build());
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void updateRecipeBenchmark() {
        recipeService.updateRecipe(recipe.getId(), UpdateRecipeRequest.builder()
                .title("updatedRecipe" + System.currentTimeMillis())
                .description("description")
                .instructions("instructions")
                .ingredients(Set.of(IngredientResource.builder()
                        .name("ingredient")
                        .quantity(QuantityResource.builder()
                                .amount(1)
                                .unit(Unit.GRAM)
                                .build())
                        .build()))
                .image(ImageResource.builder()
                        .path("image-path")
                        .build())
                .category(categoryMapper.mapCategoryToCategoryResource(categoryRepository.findById(category.getId()).orElse(null)))
                .build());
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void deleteRecipeBenchmark() {
        recipeService.deleteRecipe(recipe.getId());
    }

    @Benchmark
    @Warmup(iterations = 0)
    @Measurement(iterations = 1)
    public void commentRecipeBenchmark() {
        recipeService.commentRecipe(recipe.getId(), CommentRecipeRequest.builder()
                .content("content")
                .author("sampleAuthor")
                .build());
    }

    @Benchmark
    @Warmup(iterations = 0)
    @Measurement(iterations = 1)
    public void deleteCommentBenchmark() {
        recipe = recipeRepository.findById(recipe.getId()).orElse(null);
        if (recipe == null) {
            return;
        }

        Comment comment = recipe.getComments().stream().findFirst().orElse(null);
        if (comment == null) {
            return;
        }

        recipeService.deleteComment(recipe.getId(), comment.getId());
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void getRecipeBenchmark() {

        recipeService.getRecipe(recipeId);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void updateCategoryBenchmark() {

        categoryService.updateCategory(categoryId, UpdateCategoryRequest.builder()
                .name("category" + System.currentTimeMillis())
                .build());

    }

    @Benchmark
    @Warmup(iterations = 0)
    public void getCategoryBenchmark() {

        categoryService.getCategoryWithItsRecipes(categoryId);
    }

    @Benchmark
    @Warmup(iterations = 0)
    @Measurement(iterations = 1)
    public void deleteCategoryBenchmark() {

        categoryService.deleteCategory(categoryId);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void addListOfIngredientsBenchmark() {
        var recipe = recipeService.getRecipe(recipeId);
        List<IngredientResource> ingredients = new ArrayList<>();
        ingredients.add(IngredientResource.builder()
                .name("ingredient1")
                .quantity(QuantityResource.builder()
                        .amount(1)
                        .unit(Unit.GRAM)
                        .build())
                .build());
        ingredients.add(IngredientResource.builder()
                .name("ingredient2")
                .quantity(QuantityResource.builder()
                        .amount(1)
                        .unit(Unit.GRAM)
                        .build())
                .build());

        UpdateRecipeRequest request = UpdateRecipeRequest.builder()
                .title(recipe.title())
                .category(recipe.category())
                .ingredients(new HashSet<>(ingredients))
                .description(recipe.description())
                .image(recipe.image())
                .instructions(recipe.instructions())
                .portions(recipe.portions())
                .calorificValue(recipe.calorificValue())
                .preparationTimeInMinutes(recipe.preparationTimeInMinutes())
                .build();

        recipeService.updateRecipe(recipeId, request);
    }
}