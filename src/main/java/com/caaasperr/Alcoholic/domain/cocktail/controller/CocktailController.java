package com.caaasperr.Alcoholic.domain.cocktail.controller;

import com.caaasperr.Alcoholic._common.annotation.CheckCocktailOwner;
import com.caaasperr.Alcoholic.domain.cocktail.dto.*;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailIngredientsService;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailService;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailTagsService;
import com.caaasperr.Alcoholic.domain.comment.dto.GetCommentResponse;
import com.caaasperr.Alcoholic.domain.comment.service.CommentService;
import com.caaasperr.Alcoholic.domain.step.dto.CocktailStep;
import com.caaasperr.Alcoholic.domain.step.dto.ReorderStepRequest;
import com.caaasperr.Alcoholic.domain.step.service.StepService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
            @Valid @ModelAttribute CreateCocktailRequest request,
            Authentication authentication
    ) throws IOException {
        cocktailService.createCocktail(request, authentication);

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
            @Valid @ModelAttribute UpdateCocktailRequest request,
            Authentication authentication
    ) throws IOException{
        cocktailService.updateCocktail(id, request, authentication);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCocktail(
            @PathVariable Long id,
            Authentication authentication
    ) {
        cocktailService.deleteCocktail(id, authentication);

        return ResponseEntity.noContent().build();
    }
}
