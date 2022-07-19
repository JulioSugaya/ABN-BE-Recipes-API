package com.abn.recipeapi.repository;

import com.abn.recipeapi.entity.Ingredient;
import com.abn.recipeapi.entity.Recipe;
import com.abn.recipeapi.entity.RecipeIngredient;
import com.abn.recipeapi.enums.FoodType;
import com.abn.recipeapi.enums.IngredientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class RecipeRepositoryImpl implements RecipeRepositoryCustom{
    @Autowired
    EntityManager em;

    private final List<Integer> listNonVegetarianTypes;

    public RecipeRepositoryImpl(@Value("${non-vegetarian}") String nonVegetarian) {
        listNonVegetarianTypes = Arrays.stream(nonVegetarian.split(",")).map(
                t -> IngredientType.valueOf(t).ordinal()
        ).collect(Collectors.toList());
    }

    @Override
    public List<Recipe> searchByCriteria(Map<String, String> criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Recipe> cq = cb.createQuery(Recipe.class);

        Root<Recipe> root = cq.from(Recipe.class);

        // Sub-query IN
        Subquery<Recipe> subQuery = cq.subquery(Recipe.class);
        Root<Recipe> subRoot = subQuery.from(Recipe.class);
        Join<Recipe, RecipeIngredient> ri = subRoot.join("recipeIngredients");
        Join<RecipeIngredient, Ingredient> ingredient = ri.join("ingredient");
        subQuery.select(subRoot.get("id"));
        // Sub-query NOT IN
        Subquery<Recipe> subQueryNot = cq.subquery(Recipe.class);
        Root<Recipe> subRootNot = subQueryNot.from(Recipe.class);
        Join<Recipe, RecipeIngredient> riNot = subRootNot.join("recipeIngredients");
        Join<RecipeIngredient, Ingredient> ingredientNot = riNot.join("ingredient");
        subQueryNot.select(subRootNot.get("id"));

        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> subPredicatesIn = new ArrayList<>();
        List<Predicate> subPredicatesOut = new ArrayList<>();

        if(criteria.containsKey("serving")) {
            predicates.add(cb.equal(root.get("serving"), criteria.get("serving")));
        }

        if(criteria.containsKey("title")) {
            predicates.add(cb.like(root.get("title"), "%" + criteria.get("title") + "%"));
        }

        if(criteria.containsKey("instruction")) {
            predicates.add(cb.like(root.get("instruction"), "%" + criteria.get("instruction") + "%"));
        }

        if(criteria.containsKey("ingredientIn")) {
            String[] args = criteria.get("ingredientIn").split(",");
            subPredicatesIn.add(ingredient.get("name").in(args));
        }

        if(criteria.containsKey("type") && criteria.get("type").equals(FoodType.NON_VEGETARIAN.toString())) {
            subPredicatesIn.add(ingredient.get("ingredientType").in(listNonVegetarianTypes));
        }

        if(criteria.containsKey("ingredientOut")) {
            String[] args = criteria.get("ingredientOut").split(",");
            subPredicatesOut.add(ingredientNot.get("name").in(args));
        }

        if(criteria.containsKey("type") && criteria.get("type").equals(FoodType.VEGETARIAN.toString())) {
            subPredicatesOut.add(ingredientNot.get("ingredientType").in(listNonVegetarianTypes));
        }

        if(!subPredicatesOut.isEmpty()) {
            subQueryNot.where(cb.or(subPredicatesOut.toArray(new Predicate[0])));
            predicates.add(root.get("id").in(subQueryNot).not());
        }

        if(!subPredicatesIn.isEmpty()) {
            subQuery.where(cb.or(subPredicatesIn.toArray(new Predicate[0])));
            predicates.add(root.get("id").in(subQuery));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
