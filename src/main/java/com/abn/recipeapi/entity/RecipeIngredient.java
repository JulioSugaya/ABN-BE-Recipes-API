package com.abn.recipeapi.entity;

import com.abn.recipeapi.enums.MeasureType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name="recipe_id")
    private Recipe recipe;
    @ManyToOne
    private Ingredient ingredient;
    private float amount;
    private MeasureType measureType;
}
