package com.caaasperr.Alcoholic.domain.user.dto;

import java.time.LocalDateTime;

public record GetProfileResponse(
        String username,
        Long total_cocktails_created,
        Long total_ratings_created,
        LocalDateTime created_at
) {
}
