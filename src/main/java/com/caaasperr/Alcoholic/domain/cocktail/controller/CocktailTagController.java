package com.caaasperr.Alcoholic.domain.cocktail.controller;

import com.caaasperr.Alcoholic.domain.cocktail.dto.AddCocktailTagsRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CocktailTag;
import com.caaasperr.Alcoholic.domain.cocktail.dto.RemoveCocktailTagsRequest;
import com.caaasperr.Alcoholic.domain.cocktail.service.CocktailTagsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cocktails")
@RestController
public class CocktailTagController implements CocktailTagApi{
    private final CocktailTagsService cocktailTagsService;

    public CocktailTagController(CocktailTagsService cocktailTagsService) {
        this.cocktailTagsService = cocktailTagsService;
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<Void> addTags(
            @PathVariable Long id,
            @RequestBody AddCocktailTagsRequest request,
            Authentication authentication
    ) {
        cocktailTagsService.addTagsToCocktail(id, request, authentication);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/tags")
    public ResponseEntity<Void> deleteTags(
            @PathVariable Long id,
            @RequestBody RemoveCocktailTagsRequest request,
            Authentication authentication
    ) {
        cocktailTagsService.removeCocktailTags(id, request, authentication);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<List<CocktailTag>> getTags(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(cocktailTagsService.getCocktailTags(id));
    }
}
