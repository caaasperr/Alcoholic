package com.caaasperr.Alcoholic.domain.cocktail.dto;

import java.util.List;

public record AddCocktailIngredientsRequest(
        List<CreateCocktailIngredientsRequest> ingredients
) {
}
