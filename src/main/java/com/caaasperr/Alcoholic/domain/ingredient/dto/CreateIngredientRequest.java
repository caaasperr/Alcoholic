package com.caaasperr.Alcoholic.domain.ingredient.dto;

import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import com.caaasperr.Alcoholic.domain.maker.model.Maker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CreateIngredientRequest(
        @Schema(description = "메이커 ID", example = "1")
        Long maker_id,

        @Schema(description = "이름", example = "Jose Cuervo Especial", requiredMode = REQUIRED)
        @NotNull
        @NotBlank
        @Size(max = 100)
        String name,

        @Schema(description = "도수", example = "43")
        @DecimalMax(value = "100.0", message = "vol은 최대 100.0까지 허용됩니다.")
        @DecimalMin(value = "0", message = "vol은 최소 0까지 허용됩니다.")
        Float vol,

        @Schema(description = "타입", example = "0", requiredMode = REQUIRED)
        @NotNull
        @Size(max = 20)
        String type,

        @Schema(description = "설명", example = "Reposado 등급의 Tequila")
        @Size(max = 500)
        String description
) {
    public Ingredient toIngredient(Maker maker) {
        return Ingredient.builder()
                .maker(maker)
                .name(name)
                .type(type)
                .description(description)
                .build();
    }
}
