package com.caaasperr.Alcoholic.domain.rating.controller;

import com.caaasperr.Alcoholic._common.session.model.CustomUserDetails;
import com.caaasperr.Alcoholic.domain.rating.dto.CreateRatingRequest;
import com.caaasperr.Alcoholic.domain.rating.dto.GetCocktailRating;
import com.caaasperr.Alcoholic.domain.rating.dto.GetRatingResponse;
import com.caaasperr.Alcoholic.domain.rating.dto.UpdateRatingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Rating", description = "Rating API")
public interface RatingApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400")
            }
    )
    @Operation(summary = "평가 생성")
    ResponseEntity<Void> createRating(
            @PathVariable Long cocktailId,
            @Valid @RequestBody CreateRatingRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

    @ApiResponses (
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "평점 조회")
    ResponseEntity<GetCocktailRating> getAverageRating(
            @PathVariable Long cocktailId
    );

    @ApiResponses (
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "내 평가 조회")
    ResponseEntity<GetRatingResponse> getMyRating(
            @PathVariable Long cocktailId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @ApiResponses (
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400")
            }
    )
    @Operation(summary = "평가 수정")
    ResponseEntity<Void> updateRating(
            @PathVariable Long cocktailId,
            @Valid @RequestBody UpdateRatingRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @ApiResponses (
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "평가 삭제")
    ResponseEntity<Void> deleteRating(
            @PathVariable Long cocktailId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
