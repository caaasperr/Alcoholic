package com.caaasperr.Alcoholic.domain.comment.dto;

import com.caaasperr.Alcoholic.domain.comment.model.Comment;
import java.time.LocalDateTime;

public record GetCommentResponse(
        Long comment_id,
        Long cocktail_id,
        Long user_id,
        String content,
        LocalDateTime created_at
) {
    public static GetCommentResponse of(Comment comment) {
        return new GetCommentResponse(comment.getId(), comment.getCocktail().getId(), comment.getUser().getId(), comment.getContent(), comment.getCreatedAt());
    }
}
