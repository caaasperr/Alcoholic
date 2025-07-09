package com.caaasperr.Alcoholic.domain.cocktail.repository;

import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailIngredients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CocktailIngredientsRepository extends JpaRepository<CocktailIngredients, Long> {
    List<CocktailIngredients> findAllByCocktail_id(Long cocktailId);
}
