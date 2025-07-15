package com.caaasperr.Alcoholic.domain.cocktail.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailIngredients;

import java.util.List;

public record UpdateIngredientAmountRequest(
    List<InnerIngredientAmountEntry> ingredients
) {
    public record InnerIngredientAmountEntry(
        Long ingredient_id,
        String amount
    ) {

    }
}
