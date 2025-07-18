package com.caaasperr.Alcoholic.domain.rating.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.rating.dto.CreateRatingRequest;
import com.caaasperr.Alcoholic.domain.rating.dto.GetCocktailRating;
import com.caaasperr.Alcoholic.domain.rating.dto.GetRatingResponse;
import com.caaasperr.Alcoholic.domain.rating.dto.UpdateRatingRequest;
import com.caaasperr.Alcoholic.domain.rating.model.Rating;
import com.caaasperr.Alcoholic.domain.rating.repository.RatingRepository;
import com.caaasperr.Alcoholic.domain.user.model.User;
import com.caaasperr.Alcoholic.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final CocktailRepository cocktailRepository;

    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, CocktailRepository cocktailRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.cocktailRepository = cocktailRepository;
    }

    public void createRating(Long cocktailId, Long userId, CreateRatingRequest request) {
        Cocktail cocktail = cocktailRepository.findById(cocktailId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if (ratingRepository.findByUserAndCocktail(user, cocktail).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_RATING);
        }

        ratingRepository.save(request.toRating(user, cocktail));
    }

    public Optional<GetRatingResponse> getMyRating(Long cocktailId, Long userId) {
        return ratingRepository.findByUserIdAndCocktailId(userId, cocktailId)
                .map(r -> new GetRatingResponse(
                        cocktailId,
                        userId,
                        r.getScore(),
                        r.getDescription())
                );
    }

    @Transactional
    public void updateMyRating(Long cocktailId, Long userId, UpdateRatingRequest request) {
        Rating rating = ratingRepository.findByUserIdAndCocktailId(userId, cocktailId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RATING));

        if (request.score() != null && !rating.getScore().equals(request.score())) {
            rating.updateScore(request.score());
        }

        if (request.description() != null && !rating.getDescription().equals(request.description())) {
            rating.updateDescription(request.description());
        }

        rating.updateUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public void deleteMyRating(Long cocktailId, Long userId) {
        ratingRepository.deleteByUserIdAndCocktailId(userId, cocktailId);
    }

    public GetCocktailRating getAverageRating(Long cocktailId) {
        Float avg = ratingRepository.findAverageScoreByCocktailId(cocktailId);
        Long count = ratingRepository.countByCocktailId(cocktailId);

        return new GetCocktailRating(cocktailId, avg != null ? avg : 0.0F, count);
    }
}
