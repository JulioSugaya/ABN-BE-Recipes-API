package com.abn.recipeapi.entity;

import com.abn.recipeapi.enums.IngredientType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private IngredientType ingredientType;
    @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredient> recipeIngredients;
}
