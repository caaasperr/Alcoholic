package com.caaasperr.Alcoholic.domain.comment.controller;

import com.caaasperr.Alcoholic.domain.comment.dto.GetCommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Comment")
public interface CocktailCommentApi {
    @ApiResponse(responseCode = "200")
    @Operation(summary = "칵테일 댓글 조회")
    ResponseEntity<List<GetCommentResponse>> getComments(
            @PathVariable Long id
    );
}
