package com.abn.recipeapi.enums;

public enum IngredientType {
    VEGETABLES("Vegetables"),
    CONDIMENT("Condiment"),
    ANIMAL_PROTEIN("Animal Protein"),
    FISH("Fish"),
    FRUIT("Fruit"),
    PASTA("Pasta"),
    GRAIN("Grain"),
    GENERAL("General");

    private final String name;

    IngredientType(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
