package com.caaasperr.Alcoholic.domain.cocktail.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.step.model.Step;
import com.caaasperr.Alcoholic.domain.tag.model.Tag;

import java.time.LocalDateTime;
import java.util.List;

public record GetCocktailResponse(
        Long id,
        String username,
        String name,
        String description,
        String cover_image,
        List<CocktailTag> tags,
        List<CocktailIngredient> ingredients,
        List<Step> steps,
        Float vol,
        Long view,
        LocalDateTime created_at
) {
    public static GetCocktailResponse of(Cocktail cocktail, List<Step> steps, List<CocktailIngredient> ingredients, List<CocktailTag> tags) {
        return new GetCocktailResponse(
                cocktail.getId(),
                cocktail.getUser().getUsername(),
                cocktail.getName(),
                cocktail.getDescription(),
                cocktail.getCover_image(),
                tags,
                ingredients,
                steps, cocktail.getVol(),
                cocktail.getView(),
                cocktail.getCreated_at()
        );
    }
}
