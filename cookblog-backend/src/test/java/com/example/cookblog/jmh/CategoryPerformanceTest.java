package com.example.cookblog.jmh;

import com.example.cookblog.CookblogApplication;
import com.example.cookblog.dto.requests.CreateCategoryRequest;
import com.example.cookblog.service.CategoryUseCases;
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
public class CategoryPerformanceTest {

    private ConfigurableApplicationContext context;
    private CategoryUseCases categoryService;

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
}