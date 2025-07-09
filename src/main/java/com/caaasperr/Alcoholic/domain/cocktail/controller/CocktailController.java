package com.caaasperr.Alcoholic.domain.cocktail.controller;

import com.caaasperr.Alcoholic.domain.cocktail.dto.AddCocktailTagsRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CreateCocktailIngredientsRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CreateCocktailRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.GetCocktailResponse;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailIngredientsService;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailService;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailTagsService;
import com.caaasperr.Alcoholic.domain.step.service.StepService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cocktails")
public class CocktailController implements CocktailApi{
    private final CocktailService cocktailService;
    private final StepService stepService;
    private final CocktailIngredientsService cocktailIngredientsService;
    private final CocktailTagsService cocktailTagsService;

    public CocktailController(CocktailService cocktailService, StepService stepService, CocktailIngredientsService cocktailIngredientsService, CocktailTagsService cocktailTagsService) {
        this.cocktailService = cocktailService;
        this.stepService = stepService;
        this.cocktailIngredientsService = cocktailIngredientsService;
        this.cocktailTagsService = cocktailTagsService;
    }

    @PostMapping
    public ResponseEntity<Void> createCocktail(
            @RequestBody CreateCocktailRequest request
    ) {
        cocktailService.createCocktail(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCocktailResponse> getCocktail(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(GetCocktailResponse.of(cocktailService.getCocktail(id), stepService.getStepsByCocktailID(id), cocktailIngredientsService.getCocktailIngredients(id), cocktailTagsService.getCocktailTags(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCocktail(
            @PathVariable Long id
    ) {
        cocktailService.deleteCocktail(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/ingredients")
    public ResponseEntity<Void> addIngredient(
            @PathVariable Long id,
            @RequestBody CreateCocktailIngredientsRequest request
    ) {
        cocktailIngredientsService.createCocktailIngredients(id, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<Void> addTags(
            @PathVariable Long id,
            @RequestBody AddCocktailTagsRequest request
    ) {
        cocktailTagsService.addTagsToCocktail(id, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
