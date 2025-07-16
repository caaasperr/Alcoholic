package com.caaasperr.Alcoholic.domain.rating.dto;

import com.caaasperr.Alcoholic.domain.rating.model.Rating;

public record GetRatingResponse(
    Long cocktail_id,
    Long user_id,
    Float score,
    String description
) {
    public static GetRatingResponse fromRating(Rating rating) {
        return new GetRatingResponse(rating.getCocktail().getId(), rating.getUser().getId(), rating.getScore(), rating.getDescription());
    }
}
