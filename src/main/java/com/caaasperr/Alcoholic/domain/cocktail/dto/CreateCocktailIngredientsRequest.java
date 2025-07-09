package com.caaasperr.Alcoholic.domain.cocktail.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailIngredients;
import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;

public record CreateCocktailIngredientsRequest(
        Long ingredient_id,
        String amount
) {
    public CocktailIngredients toCocktailIngredients(Cocktail cocktail, Ingredient ingredient) {
        return CocktailIngredients.builder()
                .cocktail(cocktail)
                .ingredient(ingredient)
                .amount(amount)
                .build();
    }
}
