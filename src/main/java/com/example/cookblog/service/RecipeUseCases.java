package com.example.cookblog.service;

import com.example.cookblog.dto.requests.CommentRecipeRequest;
import com.example.cookblog.dto.requests.CreateRecipeRequest;
import com.example.cookblog.dto.requests.UpdateRecipeRequest;
import com.example.cookblog.dto.resources.RecipeResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeUseCases {

    Page<RecipeResource> getPageOfRecipes(Pageable pageable);

    List<RecipeResource> searchRecipes(String query);

    RecipeResource getRecipe(Long id);

    void createRecipe(CreateRecipeRequest createRecipeRequest);

    void updateRecipe(Long id, UpdateRecipeRequest updateRecipeRequest);

    void deleteRecipe(Long id);

    void commentRecipe(Long id, CommentRecipeRequest commentRecipeRequest);

    void deleteComment(Long id, Long commentId);

}
