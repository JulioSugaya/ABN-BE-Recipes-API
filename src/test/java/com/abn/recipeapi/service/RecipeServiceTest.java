package com.abn.recipeapi.service;

import com.abn.recipeapi.entity.Recipe;
import com.abn.recipeapi.exception.EntityNotFoundException;
import com.abn.recipeapi.repository.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

class RecipeServiceTest {

    private final RecipeRepository recipeRepository = Mockito.mock(RecipeRepository.class);
    private final IngredientService ingredientService = Mockito.mock(IngredientService.class);
    private final RecipeService recipeService = new RecipeService(recipeRepository, ingredientService);

    public Recipe createRecipe() {
        Recipe recipe01 = new Recipe();
        recipe01.setId(1L);
        recipe01.setTitle("Test1");

        return recipe01;
    }

    public List<Recipe> createListRecipe() {
        return List.of(createRecipe(), createRecipe());
    }

    @Test
    void findById_Successful() {
        Recipe recipe01 = createRecipe();

        when(recipeRepository.findById(recipe01.getId()))
                .thenReturn(Optional.of(recipe01));

        Recipe result = recipeService.findById(recipe01.getId());

        Assertions.assertEquals(result.getId(), recipe01.getId());
    }

    @Test
    void findById_But_EntityNotFound() {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    recipeService.findById(2L);
                }
        );
    }

    @Test
    void save_Successful() {
        Recipe recipe01 = createRecipe();

        when(recipeRepository.save(recipe01)).thenReturn(recipe01);

        Recipe result = recipeService.save(recipe01);

        Assertions.assertEquals(result.getId(), recipe01.getId());
    }

    @Test
    void search() {
        List<Recipe> list = createListRecipe();
        Map<String, String> criteria = new HashMap<>();
        criteria.put("test1", "teste1");

        when(recipeRepository.searchByCriteria(criteria))
                .thenReturn(list);

        List<Recipe>  result = recipeService.search(criteria);

        Assertions.assertEquals(result.size(), list.size());
    }

    @Test
    void update_Successful() {
        Recipe recipe01 = createRecipe();

        when(recipeRepository.findById(recipe01.getId()))
                .thenReturn(Optional.of(recipe01));
        when(recipeRepository.save(recipe01)).thenReturn(recipe01);

        Recipe result = recipeService.update(recipe01.getId(), recipe01);

        Assertions.assertEquals(result.getId(), recipe01.getId());
    }

    @Test
    void update_But_EntityNotFound() {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    recipeService.findById(2L);
                    recipeService.update(2L, new Recipe());
                }
        );

        verify(recipeRepository,times(0)).save(new Recipe());
    }

    @Test
    void deleteById() {
        Recipe recipe01 = createRecipe();

        when(recipeRepository.findById(recipe01.getId()))
                .thenReturn(Optional.of(recipe01));
        doNothing().when(recipeRepository).deleteById(recipe01.getId());

        recipeService.deleteById(recipe01.getId());

        verify(recipeRepository,times(1)).deleteById(recipe01.getId());
    }

    @Test
    void delete_But_EntityNotFound() {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    recipeService.findById(2L);
                    recipeService.deleteById(2L);
                }
        );

        verify(recipeRepository,times(0)).deleteById(2L);
    }
}