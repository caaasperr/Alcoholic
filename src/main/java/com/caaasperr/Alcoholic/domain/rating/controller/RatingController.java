package com.caaasperr.Alcoholic.domain.rating.controller;

import com.caaasperr.Alcoholic._common.session.model.CustomUserDetails;
import com.caaasperr.Alcoholic.domain.rating.dto.CreateRatingRequest;
import com.caaasperr.Alcoholic.domain.rating.dto.GetCocktailRating;
import com.caaasperr.Alcoholic.domain.rating.dto.GetRatingResponse;
import com.caaasperr.Alcoholic.domain.rating.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cocktails")
public class RatingController implements RatingApi {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/{cocktailId}/rating")
    public ResponseEntity<Void> createRating(
            @PathVariable Long cocktailId,
            @Valid @RequestBody CreateRatingRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        ratingService.createRating(cocktailId, customUserDetails.getId(), request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cocktailId}/rating")
    public ResponseEntity<GetCocktailRating> getAverageRating(@PathVariable Long cocktailId) {
        return ResponseEntity.ok(ratingService.getAverageRating(cocktailId));
    }

    @GetMapping("/{cocktailId}/rating/me")
    public ResponseEntity<GetRatingResponse> getMyRating(
            @PathVariable Long cocktailId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ratingService.getMyRating(cocktailId, userDetails.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{cocktailId}/rating")
    public ResponseEntity<Void> deleteRating(
            @PathVariable Long cocktailId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ratingService.deleteMyRating(cocktailId, userDetails.getId());

        return ResponseEntity.noContent().build();
    }
}
