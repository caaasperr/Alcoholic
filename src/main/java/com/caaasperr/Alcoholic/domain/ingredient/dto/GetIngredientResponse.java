package com.caaasperr.Alcoholic.domain.ingredient.dto;

import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import com.caaasperr.Alcoholic.domain.maker.dto.GetMakerResponse;

public record GetIngredientResponse(
        GetMakerResponse maker,
        String name,
        Float vol,
        String type,
        String description
) {
    public static GetIngredientResponse of(Ingredient ingredient) {
        return new GetIngredientResponse(GetMakerResponse.of(ingredient.getMaker()), ingredient.getName(), ingredient.getVol(), ingredient.getType(), ingredient.getDescription());
    }
}
