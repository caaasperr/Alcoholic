package com.caaasperr.Alcoholic._common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //Error code for auth
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "Could not find user"),
    NOT_MATCH_PASSWORD(HttpStatus.UNAUTHORIZED, "Password does not match"),

    //Error code for Cocktail
    NOT_FOUND_COCKTAIL(HttpStatus.NOT_FOUND, "Could not find cocktail"),

    //Error code for Maker
    NOT_FOUND_MAKER(HttpStatus.NOT_FOUND, "Could not find maker"),

    //Error code for Tag
    NOT_FOUND_TAG(HttpStatus.NOT_FOUND, "Could not find tag"),

    //Error code for comment
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "Could not find comment"),

    //NOT_FOUND_INGREDIENT
    NOT_FOUND_INGREDIENT(HttpStatus.NOT_FOUND, "Could not find ingredient");

    private final HttpStatus httpStatus;
    private final String message;
}