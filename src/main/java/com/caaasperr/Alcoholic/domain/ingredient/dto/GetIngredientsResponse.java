package com.caaasperr.Alcoholic.domain.ingredient.dto;

import com.caaasperr.Alcoholic._common.dto.Criteria;
import com.caaasperr.Alcoholic.domain.cocktail.dto.GetCocktailsResponse;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.math.BigInteger;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record GetIngredientsResponse(
        List<IngredientRespForGetCocktails> ingredients,
        int current_page,
        int total_page,
        int page_count,
        long total_count
) {
    public static GetIngredientsResponse of(Page<Ingredient> ingredients, Criteria criteria) {
        return new GetIngredientsResponse(
                ingredients.stream().map(IngredientRespForGetCocktails::of).toList(),
                criteria.getPage(),
                ingredients.getTotalPages(),
                ingredients.getContent().size(),
                ingredients.getTotalElements()
        );
    }

    private record IngredientRespForGetCocktails(
            String maker,
            String name,
            Float vol,
            String type,
            String description
    ) {
        public static IngredientRespForGetCocktails of(Ingredient ingredient) {
            return new IngredientRespForGetCocktails(ingredient.getMaker().getName(), ingredient.getName(), ingredient.getVol(), ingredient.getType(), ingredient.getDescription());
        }
    }
}
