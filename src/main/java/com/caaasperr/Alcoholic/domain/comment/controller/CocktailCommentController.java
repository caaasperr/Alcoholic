package com.caaasperr.Alcoholic.domain.comment.controller;

import com.caaasperr.Alcoholic.domain.comment.dto.GetCommentResponse;
import com.caaasperr.Alcoholic.domain.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cocktails")
public class CocktailCommentController implements CocktailCommentApi {
    private final CommentService commentService;

    public CocktailCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<GetCommentResponse>> getComments(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(commentService.getCommentByCocktail(id));
    }
}
