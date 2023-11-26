package com.example.cookblog.jmh;

import com.example.cookblog.CookblogApplication;
import com.example.cookblog.dto.requests.CreateCategoryRequest;
import com.example.cookblog.dto.requests.CreateRecipeRequest;
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

import java.util.Set;

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
    private Category category;

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


}