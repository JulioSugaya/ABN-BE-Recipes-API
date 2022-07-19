package com.abn.recipeapi.service;

import com.abn.recipeapi.entity.Ingredient;
import com.abn.recipeapi.enums.IngredientType;
import com.abn.recipeapi.exception.EntityNotFoundException;
import com.abn.recipeapi.repository.IngredientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class IngredientServiceTest {

    private final IngredientRepository ingredientRepository = Mockito.mock(IngredientRepository.class);
    private final IngredientService ingredientService = new IngredientService(ingredientRepository);

    public Ingredient createIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Ingredient1");
        ingredient.setIngredientType(IngredientType.VEGETABLES);

        return ingredient;
    }

    public List<Ingredient> createListRecipe() {
        return List.of(createIngredient(), createIngredient());
    }

    @Test
    void findById_Successful() {
        Ingredient ingredient = createIngredient();

        when(ingredientRepository.findById(ingredient.getId()))
                .thenReturn(Optional.of(ingredient));

        Ingredient result = ingredientService.findById(ingredient.getId());

        Assertions.assertEquals(result.getId(), ingredient.getId());
    }

    @Test
    void findById_But_EntityNotFound() {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    ingredientService.findById(2L);
                }
        );
    }

    @Test
    void save_Successful() {
        Ingredient ingredient = createIngredient();

        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

        Ingredient result = ingredientService.save(ingredient);

        Assertions.assertEquals(result.getId(), ingredient.getId());
    }

    @Test
    void update_Successful() {
        Ingredient ingredient = createIngredient();

        when(ingredientRepository.findById(ingredient.getId()))
                .thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

        Ingredient result = ingredientService.update(ingredient.getId(), ingredient);

        Assertions.assertEquals(result.getId(), ingredient.getId());
    }

    @Test
    void update_But_EntityNotFound() {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    ingredientService.findById(2L);
                    ingredientService.update(2L, new Ingredient());
                }
        );

        verify(ingredientRepository,times(0)).save(new Ingredient());
    }

    @Test
    void deleteById() {
        Ingredient recipe01 = createIngredient();

        when(ingredientRepository.findById(recipe01.getId()))
                .thenReturn(Optional.of(recipe01));
        doNothing().when(ingredientRepository).deleteById(recipe01.getId());

        ingredientService.deleteById(recipe01.getId());

        verify(ingredientRepository,times(1)).deleteById(recipe01.getId());
    }

    @Test
    void delete_But_EntityNotFound() {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    ingredientService.findById(2L);
                    ingredientService.deleteById(2L);
                }
        );

        verify(ingredientRepository,times(0)).deleteById(2L);
    }
}