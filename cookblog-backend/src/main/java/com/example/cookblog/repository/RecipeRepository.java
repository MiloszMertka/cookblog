package com.example.cookblog.repository;

import com.example.cookblog.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    boolean existsByTitle(String title);

    List<Recipe> findAllByTitleContainsIgnoreCase(String query);

    Optional<Recipe> findByTitle(String title);

}
