package com.caaasperr.Alcoholic.domain.ingredient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record UpdateIngredientRequest(
        Integer maker_id,

        @Schema(description = "이름", example = "Jose Cuervo Especial", requiredMode = REQUIRED)
        @Size(max = 100)
        String name,

        @Schema(description = "도수", example = "43.0")
        @DecimalMax(value = "100.0", message = "vol은 최대 100.0까지 허용됩니다.")
        @DecimalMin(value = "0", message = "vol은 최소 0까지 허용됩니다.")
        Float vol,

        @Size(max = 20)
        String type,

        @Size(max = 500)
        String description
) {
}
