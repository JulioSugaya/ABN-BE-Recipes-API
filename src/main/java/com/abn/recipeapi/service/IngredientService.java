package com.abn.recipeapi.service;

import com.abn.recipeapi.entity.Ingredient;
import com.abn.recipeapi.exception.EntityNotFoundException;
import com.abn.recipeapi.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(
            IngredientRepository ingredientRepository
    ) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient findById(Long id) {
        return this.ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient with ID: " + id + " not found."));
    }

    public List<Ingredient> findAll() {
        return this.ingredientRepository.findAll();
    }

    @Transactional
    public Ingredient save(Ingredient ingredient) {
        return this.ingredientRepository.save(ingredient);
    }

    @Transactional
    public Ingredient update(Long id, Ingredient ingredient) {
        this.findById(id);

        return this.ingredientRepository.save(ingredient);
    }

    @Transactional
    public void deleteById(Long id) {
        this.findById(id);

        this.ingredientRepository.deleteById(id);
    }
}
