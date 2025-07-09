package com.caaasperr.Alcoholic.domain.cocktail.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailIngredients;
import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;

public record CocktailIngredient(
        Long id,
        String name,
        String amount
) {
    public static CocktailIngredient from(CocktailIngredients ingredients) {
        return new CocktailIngredient(ingredients.getIngredient().getId(), ingredients.getIngredient().getName(), ingredients.getAmount());
    }
}
