package com.caaasperr.Alcoholic.domain.rating.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.rating.model.Rating;
import com.caaasperr.Alcoholic.domain.user.model.User;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateRatingRequest(
        @NotNull
        @NotBlank
        @DecimalMax(value = "5.0", message = "score은 최대 5.0까지 허용됩니다.")
        @DecimalMin(value = "0", message = "score은 최소 0까지 허용됩니다.")
        Float score,

        @Size(max = 500)
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
