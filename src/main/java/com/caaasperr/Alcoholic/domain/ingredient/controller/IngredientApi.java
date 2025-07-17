package com.caaasperr.Alcoholic.domain.ingredient.controller;

import com.caaasperr.Alcoholic.domain.ingredient.dto.CreateIngredientRequest;
import com.caaasperr.Alcoholic.domain.ingredient.dto.GetIngredientResponse;
import com.caaasperr.Alcoholic.domain.ingredient.dto.GetIngredientsResponse;
import com.caaasperr.Alcoholic.domain.ingredient.dto.UpdateIngredientRequest;
import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;

@Tag(name = "Ingredient", description = "Ingredient API")
public interface IngredientApi {
    @ApiResponse(responseCode = "201")
    @Operation(summary = "재료 생성")
    ResponseEntity<Void> createIngredient(
            @RequestBody CreateIngredientRequest request
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200")
            }
    )
    @Operation(summary = "모든 재료 조회")
    ResponseEntity<GetIngredientsResponse> getIngredients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "특정 재료 수정")
    ResponseEntity<Void> updateIngredient(
            @RequestParam(defaultValue = "1") Long id,
            @RequestBody UpdateIngredientRequest request
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "특정 재료 조회")
    ResponseEntity<GetIngredientResponse> getIngredient(
            @RequestParam(defaultValue = "1") Long id
    );


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204")
            }
    )
    @Operation(summary = "재료 삭제")
    ResponseEntity<Void> deleteIngredient(
            @PathVariable Long id
    );
}
