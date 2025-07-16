package com.caaasperr.Alcoholic.domain.cocktail.controller;

import com.caaasperr.Alcoholic.domain.cocktail.dto.*;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailIngredientsService;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailService;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailTagsService;
import com.caaasperr.Alcoholic.domain.comment.dto.GetCommentResponse;
import com.caaasperr.Alcoholic.domain.comment.repository.CommentRepository;
import com.caaasperr.Alcoholic.domain.comment.service.CommentService;
import com.caaasperr.Alcoholic.domain.step.dto.CocktailStep;
import com.caaasperr.Alcoholic.domain.step.service.StepService;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cocktails")
public class CocktailController implements CocktailApi{
    private final CocktailService cocktailService;
    private final CocktailIngredientsService cocktailIngredientsService;
    private final CocktailTagsService cocktailTagsService;
    private final CommentService commentService;
    private final StepService stepService;

    public CocktailController(CocktailService cocktailService, StepService stepService, CocktailIngredientsService cocktailIngredientsService, CocktailTagsService cocktailTagsService, CommentService commentService) {
        this.cocktailService = cocktailService;
        this.cocktailIngredientsService = cocktailIngredientsService;
        this.cocktailTagsService = cocktailTagsService;
        this.commentService = commentService;
        this.stepService = stepService;
    }

    @PostMapping
    public ResponseEntity<Void> createCocktail(
            @RequestBody CreateCocktailRequest request
    ) {
        cocktailService.createCocktail(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<GetCocktailsResponse> getCocktails(
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) List<String> ingredients,
            @RequestParam(required = false, defaultValue = "any") String match,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        boolean matchAll = "all".equalsIgnoreCase(match);
        GetCocktailsResponse response = cocktailService.getCocktails(page, size, matchAll, tags, ingredients);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCocktailResponse> getCocktail(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(GetCocktailResponse.of(cocktailService.getCocktail(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<GetCocktailsResponse> searchByName(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(cocktailService.searchByName(query, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCocktail(
            @PathVariable Long id,
            @RequestBody UpdateCocktailRequest request
    ) {
        cocktailService.updateCocktail(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCocktail(
            @PathVariable Long id
    ) {
        cocktailService.deleteCocktail(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ingredients")
    public ResponseEntity<Void> addIngredients(
            @PathVariable Long id,
            @RequestBody AddCocktailIngredientsRequest request
    ) {
        cocktailIngredientsService.addCocktailIngredients(id, request);

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

    @DeleteMapping("/{id}/tags")
    public ResponseEntity<Void> deleteTags(
            @PathVariable Long id,
            @RequestBody RemoveCocktailTagsRequest request
    ) {
        cocktailTagsService.removeCocktailTags(id, request);

        return ResponseEntity.noContent().build();
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
            @RequestBody UpdateIngredientAmountRequest request
    ) {
        cocktailIngredientsService.updateIngredientAmounts(id, request.ingredients());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/ingredients")
    public ResponseEntity<Void> deleteIngredients(
            @PathVariable Long id,
            @RequestBody RemoveCocktailIngredientsRequest request
    ) {
        cocktailIngredientsService.removeCocktailIngredients(id, request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<List<CocktailTag>> getTags(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(cocktailTagsService.getCocktailTags(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<GetCommentResponse>> getComments(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(commentService.getCommentByCocktail(id));
    }

    @GetMapping("/{id}/steps")
    public ResponseEntity<List<CocktailStep>> getSteps(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(stepService.getStepsByCocktailID(id));
    }
}
