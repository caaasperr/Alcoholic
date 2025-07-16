package com.caaasperr.Alcoholic.domain.rating.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.rating.model.Rating;
import com.caaasperr.Alcoholic.domain.user.model.User;

import java.time.LocalDateTime;

public record CreateRatingRequest(
        Float score,
        String description
) {
    public Rating toRating(User user, Cocktail cocktail) {
        return Rating.builder()
                .user(user)
                .cocktail(cocktail)
                .score(score)
                .description(description)
                .created_at(LocalDateTime.now())
                .build();
    }
}
