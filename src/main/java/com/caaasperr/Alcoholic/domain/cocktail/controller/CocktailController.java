package com.caaasperr.Alcoholic.domain.cocktail.controller;

import com.caaasperr.Alcoholic.domain.cocktail.dto.*;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailIngredientsService;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailService;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailTagsService;
import com.caaasperr.Alcoholic.domain.comment.dto.GetCommentResponse;
import com.caaasperr.Alcoholic.domain.comment.repository.CommentRepository;
import com.caaasperr.Alcoholic.domain.comment.service.CommentService;
import com.caaasperr.Alcoholic.domain.step.service.StepService;
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

    public CocktailController(CocktailService cocktailService, StepService stepService, CocktailIngredientsService cocktailIngredientsService, CocktailTagsService cocktailTagsService, CommentRepository commentRepository, CommentService commentService) {
        this.cocktailService = cocktailService;
        this.cocktailIngredientsService = cocktailIngredientsService;
        this.cocktailTagsService = cocktailTagsService;
        this.commentService = commentService;
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
            @RequestParam(required = false, defaultValue = "match") String match,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCocktail(
            @PathVariable Long id
    ) {
        cocktailService.deleteCocktail(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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

    @GetMapping("/{id}/ingredients")
    public ResponseEntity<List<CocktailIngredient>> getIngredients(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(cocktailIngredientsService.getCocktailIngredients(id));
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
}
