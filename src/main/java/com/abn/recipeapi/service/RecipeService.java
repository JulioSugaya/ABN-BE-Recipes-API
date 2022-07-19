package com.abn.recipeapi.service;

import com.abn.recipeapi.entity.Recipe;
import com.abn.recipeapi.entity.RecipeIngredient;
import com.abn.recipeapi.exception.EntityNotFoundException;
import com.abn.recipeapi.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final IngredientService ingredientService;

    public RecipeService(
            RecipeRepository recipeRepository,
            IngredientService ingredientService
    ) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
    }

    public List<Recipe> search(Map<String, String> criteria) {
        return this.recipeRepository.searchByCriteria(criteria);
    }

    public Recipe findById(Long id) {
        return this.recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with ID: " + id + " not found."));
    }

    @Transactional
    public Recipe save(Recipe recipe) {
        this.ingredientValidation(recipe.getRecipeIngredients());

        return this.recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe update(Long id, Recipe recipe) {
        this.findById(id);
        this.ingredientValidation(recipe.getRecipeIngredients());
        recipe.setId(id);

        return this.recipeRepository.save(recipe);
    }

    @Transactional
    public void deleteById(Long id) {
        this.findById(id);

        this.recipeRepository.deleteById(id);
    }

    public void ingredientValidation(List<RecipeIngredient> list) {
        list.forEach(recipeIngredient -> {
            this.ingredientService.findById(recipeIngredient.getIngredient().getId());
        });
    }
}
