package com.abn.recipeapi.enums;

public enum MeasureType {
    CUP("Cup"),
    TEASPOON("Teaspoon"),
    TABLESPOON("Tablespoon"),
    UNIT("Unit"),
    EMPTY(""),
    PINCH("Pinch");

    private final String name;

    MeasureType(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
