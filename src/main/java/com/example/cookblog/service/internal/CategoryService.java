package com.example.cookblog.service.internal;

import com.example.cookblog.dto.requests.CreateCategoryRequest;
import com.example.cookblog.dto.requests.UpdateCategoryRequest;
import com.example.cookblog.dto.resources.CategoryResource;
import com.example.cookblog.mapper.CategoryMapper;
import com.example.cookblog.repository.CategoryRepository;
import com.example.cookblog.service.CategoryUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class CategoryService implements CategoryUseCases {

    private static final String NAME_IS_TAKEN_ERROR_MESSAGE = "Given name is already taken";
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResource> getAllCategories() {
        final var categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::mapCategoryToCategoryResource)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResource getCategoryWithItsRecipes(Long id) {
        final var category = categoryRepository.findById(id).orElseThrow();
        return categoryMapper.mapCategoryWithRecipesToCategoryResource(category);
    }

    @Override
    @Transactional
    public void createCategory(CreateCategoryRequest createCategoryRequest) {
        checkIfNameIsNotTaken(createCategoryRequest.name());
        final var category = categoryMapper.mapCreateCategoryRequestToCategory(createCategoryRequest);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(Long id, UpdateCategoryRequest updateCategoryRequest) {
        final var category = categoryRepository.findById(id).orElseThrow();
        checkIfNameChangedAndIsNotTaken(category.getName(), updateCategoryRequest.name());
        categoryMapper.updateCategoryFromUpdateCategoryRequest(category, updateCategoryRequest);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private void checkIfNameChangedAndIsNotTaken(String previousName, String newName) {
        if (!previousName.equals(newName)) {
            checkIfNameIsNotTaken(newName);
        }
    }

    private void checkIfNameIsNotTaken(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new IllegalStateException(NAME_IS_TAKEN_ERROR_MESSAGE);
        }
    }

}
