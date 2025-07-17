package com.caaasperr.Alcoholic.domain.cocktail.controller;

import com.caaasperr.Alcoholic.domain.cocktail.dto.AddCocktailTagsRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CocktailTag;
import com.caaasperr.Alcoholic.domain.cocktail.dto.RemoveCocktailTagsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Cocktail Tag", description = "Cocktail Tag API")
public interface CocktailTagApi {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "403"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "칵테일 태그 추가")
    ResponseEntity<Void> addTags(
            @PathVariable Long id,
            @RequestBody AddCocktailTagsRequest request,
            Authentication authentication
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "403"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "칵테일 태그 삭제")
    ResponseEntity<Void> deleteTags(
            @PathVariable Long id,
            @RequestBody RemoveCocktailTagsRequest request,
            Authentication authentication
    );

    @ApiResponse(responseCode = "200")
    @Operation(summary = "칵테일 태그 조회")
    ResponseEntity<List<CocktailTag>> getTags(
            @PathVariable Long id
    );
}
