package com.caaasperr.Alcoholic.domain.cocktail.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailTags;

public record CocktailTag(
        Long id,
        String name,
        String description
) {
    public static CocktailTag from(CocktailTags tags) {
        return new CocktailTag(tags.getTag().getId(), tags.getTag().getName(), tags.getTag().getDescription());
    }
}
