package com.caaasperr.Alcoholic.domain.rating.controller;

import com.caaasperr.Alcoholic._common.session.model.CustomUserDetails;
import com.caaasperr.Alcoholic.domain.rating.dto.CreateRatingRequest;
import com.caaasperr.Alcoholic.domain.rating.dto.GetCocktailRating;
import com.caaasperr.Alcoholic.domain.rating.dto.GetRatingResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Rating", description = "Rating API")
public interface RatingApi {
    ResponseEntity<Void> createRating(
            @PathVariable Long cocktailId,
            @Valid @RequestBody CreateRatingRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

    ResponseEntity<GetCocktailRating> getAverageRating(
            @PathVariable Long cocktailId
    );

    ResponseEntity<GetRatingResponse> getMyRating(
            @PathVariable Long cocktailId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    ResponseEntity<Void> deleteRating(
            @PathVariable Long cocktailId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
