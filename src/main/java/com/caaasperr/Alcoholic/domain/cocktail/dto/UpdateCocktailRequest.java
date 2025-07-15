package com.caaasperr.Alcoholic.domain.cocktail.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

public record UpdateCocktailRequest(
        @Schema(description = "칵테일 이름", example = "피나 콜라다", requiredMode = NOT_REQUIRED)
        String name,

        @Schema(description = "칵테일 설명", example = "예시 설명", requiredMode = NOT_REQUIRED)
        String description,

        @Schema(description = "칵테일 커버 이미지", example = "example.jpg", requiredMode = NOT_REQUIRED)
        String cover_image,

        @Schema(description = "칵테일 도수", example = "5", requiredMode = NOT_REQUIRED)
        Float vol
) {
}
