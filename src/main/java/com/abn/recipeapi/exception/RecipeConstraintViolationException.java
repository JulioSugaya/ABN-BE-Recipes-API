package com.abn.recipeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecipeConstraintViolationException extends RuntimeException{

    public RecipeConstraintViolationException(String msg) {
        super(msg);
    }
}
