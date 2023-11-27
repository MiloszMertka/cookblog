package com.example.cookblog.jmh;

import com.example.cookblog.CookblogApplication;
import com.example.cookblog.dto.requests.CreateCategoryRequest;
import com.example.cookblog.dto.requests.UpdateCategoryRequest;
import com.example.cookblog.dto.resources.CategoryResource;
import com.example.cookblog.mapper.CategoryMapper;
import com.example.cookblog.model.Category;
import com.example.cookblog.repository.CategoryRepository;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class CategoryMapperPeformanceTest {
    private ConfigurableApplicationContext context;

    private CategoryMapper categoryMapper;
    private Category category;
    private CategoryRepository categoryRepository;


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
        categoryRepository = this.context.getBean(CategoryRepository.class);
        categoryMapper = this.context.getBean(CategoryMapper.class);

        String categoryName = "category" + System.currentTimeMillis();
        category = Category.builder()
                .name(categoryName)
                .build();
        categoryRepository.save(category);
    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }



}
