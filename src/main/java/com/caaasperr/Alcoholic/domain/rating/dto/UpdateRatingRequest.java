package com.caaasperr.Alcoholic.domain.rating.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public record UpdateRatingRequest(
        @DecimalMax(value = "5.0", message = "score은 최대 5.0까지 허용됩니다.")
        @DecimalMin(value = "0", message = "score은 최소 0까지 허용됩니다.")
        Float score,

        @Size(max = 500)
        String description
) {
}
