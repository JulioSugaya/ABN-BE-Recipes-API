package com.abn.recipeapi.repository;

import com.abn.recipeapi.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe,Long>, RecipeRepositoryCustom {

    @Override
    Optional<Recipe> findById(Long id);
}
