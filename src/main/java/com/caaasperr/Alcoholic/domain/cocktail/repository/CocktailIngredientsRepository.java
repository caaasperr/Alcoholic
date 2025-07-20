package com.caaasperr.Alcoholic.domain.cocktail.repository;

import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailIngredients;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CocktailIngredientsRepository extends JpaRepository<CocktailIngredients, Long> {
    @EntityGraph(attributePaths = {"ingredient"})
    List<CocktailIngredients> findAllByCocktail_id(Long cocktailId);

    void deleteByIngredient_id(Long ingredientId);

    void deleteByCocktail_Id(Long cocktailId);
}
