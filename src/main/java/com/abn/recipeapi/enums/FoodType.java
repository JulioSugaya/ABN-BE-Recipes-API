package com.abn.recipeapi.enums;

public enum FoodType {
    VEGETARIAN("Vegetarian"),
    NON_VEGETARIAN("Non-Vegetarian");

    private final String name;

    FoodType(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
