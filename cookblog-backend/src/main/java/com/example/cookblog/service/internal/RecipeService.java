package com.example.cookblog.service.internal;

import com.example.cookblog.dto.requests.CommentRecipeRequest;
import com.example.cookblog.dto.requests.CreateRecipeRequest;
import com.example.cookblog.dto.requests.UpdateRecipeRequest;
import com.example.cookblog.dto.resources.CategoryResource;
import com.example.cookblog.dto.resources.RecipeResource;
import com.example.cookblog.mapper.CategoryMapper;
import com.example.cookblog.mapper.CommentMapper;
import com.example.cookblog.mapper.RecipeMapper;
import com.example.cookblog.repository.RecipeRepository;
import com.example.cookblog.service.RecipeUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class RecipeService implements RecipeUseCases {

    private static final String TITLE_IS_TAKEN_ERROR_MESSAGE = "Given title is already taken";
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final CommentMapper commentMapper;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeResource> getPageOfRecipes(Pageable pageable) {
        final var pageOfRecipes = recipeRepository.findAll(pageable);
        return pageOfRecipes.map(recipeMapper::mapRecipeToRecipeResource);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeResource> searchRecipes(String query) {
        final var foundRecipes = recipeRepository.findAllByTitleContainsIgnoreCase(query);
        return foundRecipes.stream()
                .map(recipeMapper::mapRecipeToRecipeResource)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeResource getRecipe(Long id) {
        final var recipe = recipeRepository.findById(id).orElseThrow();
        return recipeMapper.mapRecipeToRecipeResource(recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResource getRecipeCategory(Long id) {
        final var recipe = recipeRepository.findById(id).orElseThrow();
        return categoryMapper.mapCategoryToCategoryResource(recipe.getCategory());
    }

    @Override
    @Transactional
    public void createRecipe(CreateRecipeRequest createRecipeRequest) {
        checkIfTitleIsNotTaken(createRecipeRequest.title());
        final var recipe = recipeMapper.mapCreateRecipeRequestToRecipe(createRecipeRequest);
        recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public void updateRecipe(Long id, UpdateRecipeRequest updateRecipeRequest) {
        final var recipe = recipeRepository.findById(id).orElseThrow();
        checkIfTitleChangedAndIsNotTaken(recipe.getTitle(), updateRecipeRequest.title());
        recipeMapper.updateRecipeFromUpdateRecipeRequest(recipe, updateRecipeRequest);
        recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void commentRecipe(Long id, CommentRecipeRequest commentRecipeRequest) {
        final var recipe = recipeRepository.findById(id).orElseThrow();
        final var comment = commentMapper.mapCommentRecipeRequestToComment(commentRecipeRequest);
        recipe.getComments().add(comment);
        recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public void deleteComment(Long id, Long commentId) {
        final var recipe = recipeRepository.findById(id).orElseThrow();
        final var commentToDelete = recipe.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow();
        recipe.getComments().remove(commentToDelete);
        recipeRepository.save(recipe);
    }

    private void checkIfTitleChangedAndIsNotTaken(String previousTitle, String newTitle) {
        if (!previousTitle.equals(newTitle)) {
            checkIfTitleIsNotTaken(newTitle);
        }
    }

    private void checkIfTitleIsNotTaken(String title) {
        if (recipeRepository.existsByTitle(title)) {
            throw new IllegalStateException(TITLE_IS_TAKEN_ERROR_MESSAGE);
        }
    }

}
