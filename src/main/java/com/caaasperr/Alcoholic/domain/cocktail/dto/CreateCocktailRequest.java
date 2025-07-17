package com.caaasperr.Alcoholic.domain.cocktail.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CreateCocktailRequest(
        @Schema(description = "작성자 ID", example = "1", requiredMode = REQUIRED)
        @NotNull
        Long user_id,

        @Schema(description = "칵테일 이름", example = "피나 콜라다", requiredMode = REQUIRED)
        @NotNull
        @NotBlank
        @Size(max = 100)
        String name,

        @Schema(description = "설명", example = "럼 베이스 칵테일")
        @Size(max = 500)
        String description,

        @Schema(description = "사진")
        MultipartFile cover_image,

        @Schema(description = "도수", example = "5")
        @DecimalMax(value = "100.0", message = "vol은 최대 100.0까지 허용됩니다.")
        @DecimalMin(value = "0", message = "vol은 최소 0까지 허용됩니다.")
        Float vol
) {
    public Cocktail toCocktail(User user, String cover_image_path) {
        return Cocktail.builder()
                .user(user)
                .name(name)
                .description(description)
                .cover_image(cover_image_path)
                .view(0L)
                .created_at(LocalDateTime.now())
                .vol(vol).build();
    }
}
