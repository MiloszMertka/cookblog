package com.example.cookblog.jmh;

import com.example.cookblog.CookblogApplication;
import com.example.cookblog.dto.requests.*;
import com.example.cookblog.mapper.CommentMapper;
import com.example.cookblog.model.*;
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
public class CommentMapperPerformanceTest {

    private ConfigurableApplicationContext context;

    private CommentMapper mapper;
    private Comment comment;
    private CommentRecipeRequest commentRequest;


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

        mapper = this.context.getBean(CommentMapper.class);
    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }


    @Benchmark
    @Warmup(iterations = 0)
    public void mapCommentToCommentResourceBenchmark() {
        String uniqueCommentContent = "comment" + System.currentTimeMillis();
        comment = Comment.builder()
            .content(uniqueCommentContent)
            .author("author")
            .build();

        mapper.mapCommentToCommentResource(comment);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void mapCommentRecipeRequestToCommentBenchmark() {
        String uniqueCommentContent = "comment" + System.currentTimeMillis();
        commentRequest = CommentRecipeRequest.builder()
                .content(uniqueCommentContent)
                .author("author")
                .build();

        mapper.mapCommentRecipeRequestToComment(commentRequest);
    }
}