package com.abn.recipeapi.repository;

import com.abn.recipeapi.entity.Recipe;

import java.util.List;
import java.util.Map;

public interface RecipeRepositoryCustom {
    public List<Recipe> searchByCriteria(Map<String,String> criteria);
}
