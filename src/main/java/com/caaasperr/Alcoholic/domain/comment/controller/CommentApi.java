package com.caaasperr.Alcoholic.domain.comment.controller;

import com.caaasperr.Alcoholic.domain.comment.dto.CreateCommentRequest;
import com.caaasperr.Alcoholic.domain.comment.dto.GetCommentResponse;
import com.caaasperr.Alcoholic.domain.comment.dto.UpdateCommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Comment", description = "Comment API")
public interface CommentApi {
    @ApiResponse(responseCode = "201")
    @Operation(summary = "댓글 생성")
    ResponseEntity<Void> createComment(
            @RequestBody CreateCommentRequest request
    );

    @ApiResponses (
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "댓글 조회")
    ResponseEntity<GetCommentResponse> getComment(
            @PathVariable Long id
    );

    @ApiResponses (
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "댓글 수정")
    ResponseEntity<Void> updateComment(
            @PathVariable Long id,
            @RequestBody UpdateCommentRequest request
    );

    @ApiResponses (
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "댓글 삭제")
    ResponseEntity<Void> deleteComment(
            @PathVariable Long id
    );
}
