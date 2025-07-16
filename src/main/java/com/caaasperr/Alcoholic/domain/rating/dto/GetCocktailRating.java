package com.caaasperr.Alcoholic.domain.rating.dto;

public record GetCocktailRating(
        Long cocktail_id,
        Float average_rating,
        Long rate_count
) {
}
