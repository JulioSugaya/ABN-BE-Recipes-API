package com.abn.recipeapi.util;

import com.abn.recipeapi.enums.MeasureType;
import com.abn.recipeapi.model.IngredientDto;
import com.abn.recipeapi.model.RecipeDto;
import com.abn.recipeapi.model.RecipeIngredientDto;

import java.util.List;

public class TestUtil {


    public static RecipeDto createRecipeWithInvalidIngredient() {
        RecipeDto newRecipe1 = new RecipeDto();
        newRecipe1.setTitle("boiled egg");
        newRecipe1.setInstruction("instruction 1");
        newRecipe1.setDescription("just a boiled egg");
        newRecipe1.setServing(1);

        IngredientDto ingredient1 = new IngredientDto();
        ingredient1.setId(100L); // absent

        RecipeIngredientDto ri1 = new RecipeIngredientDto();
        ri1.setAmount(2);
        ri1.setIngredient(ingredient1);
        ri1.setMeasureType(MeasureType.PINCH.name());

        newRecipe1.setRecipeIngredients(List.of(ri1));

        return newRecipe1;
    }

    public static RecipeDto createRecipeVegetarian() {
        RecipeDto newRecipe1 = new RecipeDto();
        newRecipe1.setTitle("boiled egg");
        newRecipe1.setInstruction("boil the egg on the stove for about 10 min");
        newRecipe1.setDescription("just a boiled egg");
        newRecipe1.setServing(1);

        IngredientDto ingredient1 = new IngredientDto();
        ingredient1.setId(1L); // salt
        IngredientDto ingredient2 = new IngredientDto();
        ingredient2.setId(2L); // egg

        RecipeIngredientDto ri1 = new RecipeIngredientDto();
        ri1.setAmount(2);
        ri1.setIngredient(ingredient1);
        ri1.setMeasureType(MeasureType.PINCH.name());

        RecipeIngredientDto ri2 = new RecipeIngredientDto();
        ri2.setAmount(1);
        ri2.setIngredient(ingredient2);
        ri2.setMeasureType(MeasureType.UNIT.name());

        newRecipe1.setRecipeIngredients(List.of(ri1, ri2));

        return newRecipe1;
    }

    public static RecipeDto createRecipeNonVegetarian() {
        RecipeDto newRecipe = new RecipeDto();
        newRecipe.setTitle("Rost Chicken");
        newRecipe.setInstruction("instruction : owen");
        newRecipe.setDescription("just a rost chicken");
        newRecipe.setServing(2);

        IngredientDto ingredient1 = new IngredientDto();
        ingredient1.setId(1L); // salt
        IngredientDto ingredient2 = new IngredientDto();
        ingredient2.setId(3L); // chicken
        IngredientDto ingredient3 = new IngredientDto();
        ingredient3.setId(5L); // potato

        RecipeIngredientDto ri1 = new RecipeIngredientDto();
        ri1.setAmount(1);
        ri1.setIngredient(ingredient1);
        ri1.setMeasureType(MeasureType.PINCH.name());

        RecipeIngredientDto ri2 = new RecipeIngredientDto();
        ri2.setAmount(1);
        ri2.setIngredient(ingredient2);
        ri2.setMeasureType(MeasureType.UNIT.name());

        RecipeIngredientDto ri3 = new RecipeIngredientDto();
        ri3.setAmount(4);
        ri3.setIngredient(ingredient3);
        ri3.setMeasureType(MeasureType.UNIT.name());

        newRecipe.setRecipeIngredients(List.of(ri1, ri2, ri3));

        return newRecipe;
    }

    public static RecipeDto createRecipeNonVegetarian2() {
        RecipeDto newRecipe = new RecipeDto();
        newRecipe.setTitle("Ribs and Potatoes");
        newRecipe.setInstruction("instruction : just cook on owen");
        newRecipe.setDescription("Ribs and Potatoes");
        newRecipe.setServing(1);

        IngredientDto ingredient1 = new IngredientDto();
        ingredient1.setId(7L);
        IngredientDto ingredient2 = new IngredientDto();
        ingredient2.setId(5L);

        RecipeIngredientDto ri1 = new RecipeIngredientDto();
        ri1.setAmount(1);
        ri1.setIngredient(ingredient1);
        ri1.setMeasureType(MeasureType.PINCH.name());

        RecipeIngredientDto ri2 = new RecipeIngredientDto();
        ri2.setAmount(1);
        ri2.setIngredient(ingredient2);
        ri2.setMeasureType(MeasureType.UNIT.name());

        newRecipe.setRecipeIngredients(List.of(ri1, ri2));

        return newRecipe;
    }

    public static RecipeDto createRecipeToUpdate() {
        RecipeDto newRecipe = new RecipeDto();
        newRecipe.setTitle("Ribs with potatoes and pineapple");
        newRecipe.setInstruction("instruction : owen");
        newRecipe.setDescription("Ribs with potatoes and pineapple");
        newRecipe.setServing(4);

        IngredientDto ingredient1 = new IngredientDto();
        ingredient1.setId(7L);
        IngredientDto ingredient2 = new IngredientDto();
        ingredient2.setId(5L);
        IngredientDto ingredient3 = new IngredientDto();
        ingredient3.setId(8L);

        RecipeIngredientDto ri1 = new RecipeIngredientDto();
        ri1.setAmount(1);
        ri1.setIngredient(ingredient1);
        ri1.setMeasureType(MeasureType.UNIT.name());

        RecipeIngredientDto ri2 = new RecipeIngredientDto();
        ri2.setAmount(3);
        ri2.setIngredient(ingredient2);
        ri2.setMeasureType(MeasureType.UNIT.name());

        RecipeIngredientDto ri3 = new RecipeIngredientDto();
        ri3.setAmount(1);
        ri3.setIngredient(ingredient3);
        ri3.setMeasureType(MeasureType.UNIT.name());

        newRecipe.setRecipeIngredients(List.of(ri1, ri2, ri3));

        return newRecipe;
    }

    public static String recipeToUpdateJson = "{\"id\":null,\"title\":\"Ribs with potatoes and pineapple and Mushroom\"," +
            "\"description\":\"Ribs with potatoes and pineapple and Mushroom added\",\"serving\":4," +
            "\"instruction\":\"instruction : owen\",\"calories\":0,\"cookingTime\":0,\"preparingTime\":0," +
            "\"ingredients\":[" +
            "{\"id\":1,\"amount\":1.0,\"measureType\":\"UNIT\",\"ingredient\":{\"name\":null," +
            "\"ingredientType\":null,\"id\":7},\"recipeId\":1}," +
            "{\"id\":2,\"amount\":1.0," + "\"measureType\":\"UNIT\",\"ingredient\":{\"name\":null,\"ingredientType\":null,\"id\":5}," +
            "\"recipeId\":1}," +
            "{\"id\":3,\"amount\":0.0,\"measureType\":null,\"ingredient\":{\"name\":null,\"ingredientType\":null,\"id\":8}," +
            "\"recipeId\":1}," +
            "{\"amount\":0.0,\"measureType\":null,\"ingredient\":{\"name\":null,\"ingredientType\":null,\"id\":11},\"recipeId\":1}]}";
}
