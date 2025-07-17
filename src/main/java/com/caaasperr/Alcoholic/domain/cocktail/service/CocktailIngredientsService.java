package com.caaasperr.Alcoholic.domain.cocktail.service;

import com.caaasperr.Alcoholic._common.annotation.CheckCocktailOwner;
import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.dto.*;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailIngredients;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailIngredientsRepository;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import com.caaasperr.Alcoholic.domain.ingredient.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
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

    @CheckCocktailOwner
    public void addCocktailIngredients(Long cocktail_id, AddCocktailIngredientsRequest request, Authentication authentication) {
        Cocktail cocktail = cocktailRepository.findById(cocktail_id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));

        for (CreateCocktailIngredientsRequest ingredientsRequest : request.ingredients()) {
            Ingredient ingredient = ingredientRepository.findById(ingredientsRequest.ingredient_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INGREDIENT));

            cocktailIngredientsRepository.save(CocktailIngredients.builder()
                    .cocktail(cocktail)
                    .ingredient(ingredient)
                    .amount(ingredientsRequest.amount())
                    .build()
            );
        }
    }

    @CheckCocktailOwner
    @Transactional
    public void updateIngredientAmounts(Long cocktailId, List<UpdateIngredientAmountRequest.InnerIngredientAmountEntry> entries, Authentication authentication) {
        Cocktail cocktail = cocktailRepository.findById(cocktailId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));

        List<CocktailIngredients> cocktailIngredients = cocktail.getIngredients();

        for (UpdateIngredientAmountRequest.InnerIngredientAmountEntry entry : entries) {
            CocktailIngredients matched = cocktailIngredients.stream()
                    .filter(ci -> ci.getIngredient().getId().equals(entry.ingredient_id()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INGREDIENT));

            matched.updateAmount(entry.amount());
        }
    }

    @CheckCocktailOwner
    public void removeCocktailIngredients(Long cocktail_id, RemoveCocktailIngredientsRequest request, Authentication authentication) {
        Cocktail cocktail = cocktailRepository.findById(cocktail_id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));

        List<CocktailIngredients> ingredientsToRemove = cocktailIngredientsRepository.findAllById(request.ingredientIds());

        if (ingredientsToRemove.size() != request.ingredientIds().size()) {
            throw new CustomException(ErrorCode.NOT_FOUND_INGREDIENT);
        }

        cocktail.getIngredients().removeAll(ingredientsToRemove);
    }

    public List<CocktailIngredient> getCocktailIngredients(Long cocktailId) {
        List<CocktailIngredients> ingredients = cocktailIngredientsRepository.findAllByCocktail_id(cocktailId);

        return ingredients.stream()
                .map(CocktailIngredient::from)
                .collect(Collectors.toList());
    }
}
