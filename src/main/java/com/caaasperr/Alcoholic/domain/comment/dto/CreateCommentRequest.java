package com.caaasperr.Alcoholic.domain.comment.dto;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.comment.model.Comment;
import com.caaasperr.Alcoholic.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CreateCommentRequest(
        @Schema(description = "칵테일 ID", example = "1", requiredMode = REQUIRED)
        @NotNull
        Long cocktail_id,

        @Schema(description = "유저 이름", example = "swagger", requiredMode = REQUIRED)
        @NotNull
        String username,

        @Schema(description = "내용", example = "인상깊은 레시피입니다.", requiredMode = REQUIRED)
        @NotNull
        String content
) {
    public Comment toComment(Cocktail cocktail, User user) {
        return Comment.builder()
                .cocktail(cocktail)
                .user(user)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
