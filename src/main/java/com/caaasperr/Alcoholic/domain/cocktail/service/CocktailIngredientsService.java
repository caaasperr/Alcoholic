package com.caaasperr.Alcoholic.domain.cocktail.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CocktailIngredient;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CreateCocktailIngredientsRequest;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailIngredients;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailIngredientsRepository;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import com.caaasperr.Alcoholic.domain.ingredient.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CocktailIngredientsService {
    private final CocktailIngredientsRepository cocktailIngredientsRepository;
    private final CocktailRepository cocktailRepository;
    private final IngredientRepository ingredientRepository;

    public CocktailIngredientsService(CocktailIngredientsRepository cocktailIngredientsRepository, CocktailRepository cocktailRepository, IngredientRepository ingredientRepository) {
        this.cocktailIngredientsRepository = cocktailIngredientsRepository;
        this.cocktailRepository = cocktailRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public void createCocktailIngredients(Long cocktail_id, CreateCocktailIngredientsRequest request) {
        Cocktail cocktail = cocktailRepository.findById(cocktail_id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));
        Ingredient ingredient = ingredientRepository.findById(request.ingredient_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INGREDIENT));
        cocktailIngredientsRepository.save(request.toCocktailIngredients(cocktail, ingredient));
    }

    public List<CocktailIngredient> getCocktailIngredients(Long cocktailId) {
        List<CocktailIngredients> ingredients = cocktailIngredientsRepository.findAllByCocktail_id(cocktailId);

        return ingredients.stream()
                .map(CocktailIngredient::from)
                .collect(Collectors.toList());
    }
}
