package com.caaasperr.Alcoholic.domain.cocktail.controller;

import com.caaasperr.Alcoholic.domain.cocktail.dto.AddCocktailIngredientsRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CocktailIngredient;
import com.caaasperr.Alcoholic.domain.cocktail.dto.RemoveCocktailIngredientsRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.UpdateIngredientAmountRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Cocktail Ingredient", description = "Cocktail Ingredient API")
public interface CocktailIngredientApi {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "403"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @ApiResponse(responseCode = "201")
    @Operation(summary = "칵테일 레시피 재료 추가")
    ResponseEntity<Void> addIngredients(
            @PathVariable Long id,
            @RequestBody AddCocktailIngredientsRequest request,
            Authentication authentication
    );

    @ApiResponse(responseCode = "200")
    @Operation(summary = "칵테일 재료 조회")
    ResponseEntity<List<CocktailIngredient>> getIngredients(
            @PathVariable Long id
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "403"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "칵테일 재료 수정")
    ResponseEntity<Void> updateIngredients(
            @PathVariable Long id,
            @RequestBody UpdateIngredientAmountRequest request,
            Authentication authentication
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "403"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "칵테일 재료 삭제")
    ResponseEntity<Void> deleteIngredients(
            @PathVariable Long id,
            @RequestBody RemoveCocktailIngredientsRequest request,
            Authentication authentication
    );
}
