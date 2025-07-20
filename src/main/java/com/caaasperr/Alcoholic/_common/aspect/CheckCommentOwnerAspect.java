package com.caaasperr.Alcoholic._common.aspect;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic._common.session.model.CustomUserDetails;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.comment.model.Comment;
import com.caaasperr.Alcoholic.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckCommentOwnerAspect {
    private final CommentRepository commentRepository;

    @Before(
            value = "@annotation(com.caaasperr.Alcoholic._common.annotation.CheckCommentOwner) && args(commentId, .., authentication)",
            argNames = "commentId,authentication"
    )
    public void checkCommentOwner(Long commentId, Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
    }
}
