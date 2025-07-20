package com.caaasperr.Alcoholic.domain.rating.repository;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.rating.dto.GetRatingResponse;
import com.caaasperr.Alcoholic.domain.rating.model.Rating;
import com.caaasperr.Alcoholic.domain.user.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserAndCocktail(User user, Cocktail cocktail);
    Optional<Rating> findByUserIdAndCocktailId(Long userId, Long cocktailId);
    void deleteByUserIdAndCocktailId(Long userId, Long cocktailId);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.cocktail.id = :cocktailId")
    Float findAverageScoreByCocktailId(@Param("cocktailId") Long cocktailId);

    Long countByCocktailId(Long cocktailId);

    Long countByUser_Id(Long userId);

    @EntityGraph(attributePaths = {"user", "cocktail"})
    @Query("SELECT r.cocktail.id, AVG(r.score) FROM Rating r WHERE r.cocktail.id IN :cocktailIds GROUP BY r.cocktail.id")
    List<Object[]> findAverageRatingByCocktailIds(@Param("cocktailIds") List<Long> cocktailIds);

    @EntityGraph(attributePaths = {"user", "cocktail"})
    List<Rating> findAllByUser_Id(Long userId);

    void deleteByUser_Id(Long userId);

    void deleteByCocktail_Id(Long cocktailId);
}
