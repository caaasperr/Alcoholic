package com.caaasperr.Alcoholic.domain.step.controller;

import com.caaasperr.Alcoholic._common.annotation.CheckCocktailOwner;
import com.caaasperr.Alcoholic.domain.step.dto.CocktailStep;
import com.caaasperr.Alcoholic.domain.step.dto.ReorderStepRequest;
import com.caaasperr.Alcoholic.domain.step.service.StepService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cocktails")
public class CocktailStepController implements CocktailStepApi {
    private final StepService stepService;

    public CocktailStepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/{id}/steps")
    public ResponseEntity<List<CocktailStep>> getSteps(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(stepService.getStepsByCocktailID(id));
    }

    @CheckCocktailOwner
    @PutMapping("/{id}/steps/reorder")
    public ResponseEntity<Void> reorderSteps(
            @PathVariable Long id,
            @RequestBody ReorderStepRequest request,
            Authentication authentication
    ) {
        stepService.reorderSteps(id, request.stepIds());

        return ResponseEntity.ok().build();
    }
}
