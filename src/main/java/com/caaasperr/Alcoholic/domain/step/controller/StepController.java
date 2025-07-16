package com.caaasperr.Alcoholic.domain.step.controller;

import com.caaasperr.Alcoholic.domain.step.dto.CreateStepRequest;
import com.caaasperr.Alcoholic.domain.step.dto.UpdateStepRequest;
import com.caaasperr.Alcoholic.domain.step.model.Step;
import com.caaasperr.Alcoholic.domain.step.service.StepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/steps")
public class StepController implements StepApi {
    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping
    public ResponseEntity<Void> createStep(
            @RequestBody CreateStepRequest request
    ) {
        stepService.createStep(request);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStep(
            @PathVariable Long id,
            @RequestBody UpdateStepRequest request
    ) {
        stepService.updateStep(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(
            @PathVariable Long id
    ) {
        stepService.deleteStep(id);

        return ResponseEntity.noContent().build();
    }
}
