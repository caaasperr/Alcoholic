package com.caaasperr.Alcoholic.domain.cocktail.controller;

import com.caaasperr.Alcoholic.domain.cocktail.dto.AddCocktailIngredientsRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CocktailIngredient;
import com.caaasperr.Alcoholic.domain.cocktail.dto.RemoveCocktailIngredientsRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.UpdateIngredientAmountRequest;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailIngredientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cocktails")
@RestController
public class CocktailIngredientController implements CocktailIngredientApi {
    private final CocktailIngredientsService cocktailIngredientsService;

    public CocktailIngredientController(CocktailIngredientsService cocktailIngredientsService) {
        this.cocktailIngredientsService = cocktailIngredientsService;
    }

    @PostMapping("/{id}/ingredients")
    public ResponseEntity<Void> addIngredients(
            @PathVariable Long id,
            @RequestBody AddCocktailIngredientsRequest request,
            Authentication authentication
    ) {
        cocktailIngredientsService.addCocktailIngredients(id, request, authentication);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/ingredients")
    public ResponseEntity<List<CocktailIngredient>> getIngredients(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(cocktailIngredientsService.getCocktailIngredients(id));
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<Void> updateIngredients(
            @PathVariable Long id,
            @RequestBody UpdateIngredientAmountRequest request,
            Authentication authentication
    ) {
        cocktailIngredientsService.updateIngredientAmounts(id, request.ingredients(), authentication);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/ingredients")
    public ResponseEntity<Void> deleteIngredients(
            @PathVariable Long id,
            @RequestBody RemoveCocktailIngredientsRequest request,
            Authentication authentication
    ) {
        cocktailIngredientsService.removeCocktailIngredients(id, request, authentication);

        return ResponseEntity.noContent().build();
    }
}
