package com.caaasperr.Alcoholic.domain.maker.dto;

import com.caaasperr.Alcoholic.domain.maker.model.Maker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CreateMakerRequest(
        @Schema(description = "이름", example = "Jose Cuervo", requiredMode = REQUIRED)
        @NotNull
        String name,

        @Schema(description = "국가", example = "Mexico", requiredMode = REQUIRED)
        @NotNull
        String country,

        @Schema(description = "종류", example = "Tequila", requiredMode = REQUIRED)
        @NotNull
        String type,

        @Schema(description = "설명", example = "Jose Cuervo is tequila maker.", requiredMode = REQUIRED)
        @NotNull
        String description
) {
    public Maker toMaker() {
        return Maker.builder()
                .name(name)
                .country(country)
                .type(type)
                .description(description)
                .build();
    }
}
