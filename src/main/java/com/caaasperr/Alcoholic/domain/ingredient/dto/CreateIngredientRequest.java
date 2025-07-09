package com.caaasperr.Alcoholic.domain.ingredient.dto;

import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import com.caaasperr.Alcoholic.domain.maker.model.Maker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CreateIngredientRequest(
        @Schema(description = "메이커 ID", example = "1")
        Long maker_id,

        @Schema(description = "이름", example = "Jose Cuervo Especial", requiredMode = REQUIRED)
        @NotNull
        String name,

        @Schema(description = "도수", example = "43")
        Float vol,

        @Schema(description = "타입", example = "0", requiredMode = REQUIRED)
        @NotNull
        String type,

        @Schema(description = "설명", example = "Reposado 등급의 Tequila")
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
