package com.caaasperr.Alcoholic.domain.cocktail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

public record UpdateCocktailRequest(
        @Schema(description = "칵테일 이름", example = "피나 콜라다", requiredMode = NOT_REQUIRED)
        @Size(max = 100)
        String name,

        @Schema(description = "칵테일 설명", example = "예시 설명", requiredMode = NOT_REQUIRED)
        @Size(max = 500)
        String description,

        @Schema(description = "칵테일 커버 이미지", requiredMode = NOT_REQUIRED)
        MultipartFile cover_image,

        @Schema(description = "칵테일 도수", example = "5", requiredMode = NOT_REQUIRED)
        @DecimalMax(value = "100.0", message = "vol은 최대 100.0까지 허용됩니다.")
        @DecimalMin(value = "0", message = "vol은 최소 0까지 허용됩니다.")
        Float vol
) {
}
