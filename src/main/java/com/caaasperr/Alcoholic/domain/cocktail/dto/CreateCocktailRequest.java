package com.caaasperr.Alcoholic.domain.cocktail.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CreateCocktailRequest(
        @Schema(description = "작성자 ID", example = "1", requiredMode = REQUIRED)
        @NotNull
        Long user_id,

        @Schema(description = "칵테일 이름", example = "피나 콜라다", requiredMode = REQUIRED)
        @NotNull
        String name,

        @Schema(description = "설명", example = "럼 베이스 칵테일")
        String description,

        @Schema(description = "사진 URL", example = "https://example.com/exmaple.jpg")
        String cover_image,

        @Schema(description = "도수", example = "5")
        Float vol
) {
    public Cocktail toCocktail(User user) {
        return Cocktail.builder()
                .user(user)
                .name(name)
                .description(description)
                .cover_image(cover_image)
                .view(0L)
                .created_at(LocalDateTime.now())
                .vol(vol).build();
    }
}
