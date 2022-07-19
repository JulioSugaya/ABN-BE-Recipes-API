package com.abn.recipeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RecipeIngredientDto {
    private Long id;
    private float amount;
    private String measureType;
    private IngredientDto ingredient;
    @JsonProperty("recipeId")
    private Long recipeId;
}