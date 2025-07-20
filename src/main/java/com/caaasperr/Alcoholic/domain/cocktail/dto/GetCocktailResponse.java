package com.caaasperr.Alcoholic.domain.cocktail.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.step.dto.CocktailStep;
import com.caaasperr.Alcoholic.domain.step.model.Step;

import java.time.LocalDateTime;
import java.util.List;

public record GetCocktailResponse(
        Long id,
        String username,
        String name,
        String description,
        Float average_rating,
        String cover_image,
        List<CocktailTag> tags,
        List<CocktailIngredient> ingredients,
        List<CocktailStep> steps,
        Float vol,
        Long view,
        LocalDateTime created_at
) {
    public static GetCocktailResponse of(Cocktail cocktail, Float averageRating) {
        List<CocktailTag> tags = cocktail.getTags().stream().map(CocktailTag::from).toList();
        List<CocktailIngredient> ingredients = cocktail.getIngredients().stream().map(CocktailIngredient::from).toList();
        List<CocktailStep> steps = cocktail.getSteps().stream().map(CocktailStep::from).toList();

        return new GetCocktailResponse(
                cocktail.getId(),
                cocktail.getUser().getUsername(),
                cocktail.getName(),
                cocktail.getDescription(),
                averageRating,
                cocktail.getCover_image(),
                tags,
                ingredients,
                steps, cocktail.getVol(),
                cocktail.getView(),
                cocktail.getCreated_at()
        );
    }
}
