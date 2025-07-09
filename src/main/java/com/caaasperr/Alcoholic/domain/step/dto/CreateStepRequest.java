package com.caaasperr.Alcoholic.domain.step.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.step.model.Step;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CreateStepRequest(
        @Schema(description = "칵테일 ID", example = "1", requiredMode = REQUIRED)
        @NotNull
        Long cocktail_id,

        @Schema(description = "내용", example = "재료를 모두 넣고 섞는다", requiredMode = REQUIRED)
        @NotNull
        String content,

        @Schema(description = "이미지 URL", example = "example.com/example.jpg")
        String image,

        @Schema(description = "단계 순서", example = "0", requiredMode = REQUIRED)
        @NotNull
        Integer order
) {
    public Step toStep(Cocktail cocktail) {
        return Step.builder()
                .cocktail(cocktail)
                .content(content)
                .image(image)
                .order(order)
                .build();
    }
}
