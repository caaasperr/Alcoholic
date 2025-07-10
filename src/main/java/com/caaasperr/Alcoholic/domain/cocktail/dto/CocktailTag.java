package com.caaasperr.Alcoholic.domain.cocktail.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailTags;

public record CocktailTag(
        Long id,
        String name
) {
    public static CocktailTag from(CocktailTags tags) {
        return new CocktailTag(tags.getTag().getId(), tags.getTag().getName());
    }
}
