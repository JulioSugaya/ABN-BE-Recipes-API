package com.abn.recipeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class RecipeDto {
    private Long id;
    private String title;
    private String description;
    private int serving;
    private String instruction;
    private int calories;
    private int cookingTime;
    private int preparingTime;
    @JsonProperty("ingredients")
    private List<RecipeIngredientDto> recipeIngredients = new ArrayList<>();
}
