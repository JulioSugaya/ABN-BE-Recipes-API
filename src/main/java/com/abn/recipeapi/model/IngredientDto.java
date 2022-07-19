package com.abn.recipeapi.model;

import com.abn.recipeapi.enums.IngredientType;
import lombok.Data;

@Data
public class IngredientDto {
    private Long Id;
    private String name;
    private IngredientType ingredientType;
}
