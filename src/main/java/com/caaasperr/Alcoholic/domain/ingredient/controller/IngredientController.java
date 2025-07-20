package com.caaasperr.Alcoholic.domain.ingredient.controller;

import com.caaasperr.Alcoholic.domain.ingredient.dto.CreateIngredientRequest;
import com.caaasperr.Alcoholic.domain.ingredient.dto.GetIngredientResponse;
import com.caaasperr.Alcoholic.domain.ingredient.dto.GetIngredientsResponse;
import com.caaasperr.Alcoholic.domain.ingredient.dto.UpdateIngredientRequest;
import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import com.caaasperr.Alcoholic.domain.ingredient.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
public class IngredientController implements IngredientApi {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createIngredient(
            @Valid @RequestBody CreateIngredientRequest request
    ) {
        ingredientService.createIngredient(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GetIngredientsResponse> getIngredients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ingredientService.getIngredients(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetIngredientResponse> getIngredient(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ingredientService.getIngredient(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateIngredient(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIngredientRequest request
    ) {
        ingredientService.updateIngredient(id, request);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(
            @PathVariable Long id
    ) {
        ingredientService.deleteIngredient(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
