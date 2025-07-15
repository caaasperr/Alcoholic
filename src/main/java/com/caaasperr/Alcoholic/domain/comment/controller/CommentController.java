package com.caaasperr.Alcoholic.domain.comment.controller;

import com.caaasperr.Alcoholic.domain.comment.dto.CreateCommentRequest;
import com.caaasperr.Alcoholic.domain.comment.dto.GetCommentResponse;
import com.caaasperr.Alcoholic.domain.comment.dto.UpdateCommentRequest;
import com.caaasperr.Alcoholic.domain.comment.model.Comment;
import com.caaasperr.Alcoholic.domain.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController implements CommentApi {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Void> createComment(
            @RequestBody CreateCommentRequest request
    ) {
        commentService.createComment(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCommentResponse> getComment(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long id,
            @RequestBody UpdateCommentRequest request
    ) {
        commentService.updateComment(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id
    ) {
        commentService.deleteComment(id);

        return ResponseEntity.noContent().build();
    }
}
