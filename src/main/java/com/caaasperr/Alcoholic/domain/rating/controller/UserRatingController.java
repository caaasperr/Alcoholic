package com.caaasperr.Alcoholic.domain.rating.controller;

import com.caaasperr.Alcoholic.domain.rating.dto.GetCocktailRating;
import com.caaasperr.Alcoholic.domain.rating.dto.GetRatingResponse;
import com.caaasperr.Alcoholic.domain.rating.service.RatingService;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRatingController implements UserRatingApi {
    private final RatingService ratingService;

    public UserRatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/{userId}/rating")
    public ResponseEntity<List<GetRatingResponse>> getUserRatings(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(ratingService.getMyRatings(userId));
    }
}
