package com.caaasperr.Alcoholic.domain.cocktail.controller;

import com.caaasperr.Alcoholic.domain.cocktail.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Cocktail", description = "Cocktail API")
public interface CocktailApi {
    @ApiResponse(responseCode = "201")
    @Operation(summary = "칵테일 생성")
    ResponseEntity<Void> createCocktail(
            @RequestBody CreateCocktailRequest request
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "칵테일 모두 조회")
    ResponseEntity<GetCocktailsResponse> getCocktails(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "칵테일 조회")
    ResponseEntity<GetCocktailResponse> getCocktail(
            @PathVariable Long id
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204")
            }
    )
    @Operation(summary = "칵테일 삭제")
    ResponseEntity<Void> deleteCocktail(
            @PathVariable Long id
    );

    @ApiResponse(responseCode = "201")
    @Operation(summary = "칵테일 레시피 재료 추가")
    ResponseEntity<Void> addIngredient(
            @PathVariable Long id,
            @RequestBody CreateCocktailIngredientsRequest request
    );

    @ApiResponse(responseCode = "201")
    @Operation(summary = "칵테일 태그 추가")
    ResponseEntity<Void> addTags(
            @PathVariable Long id,
            @RequestBody AddCocktailTagsRequest request
    );
}
