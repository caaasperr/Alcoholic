package com.caaasperr.Alcoholic.domain.step.controller;

import com.caaasperr.Alcoholic.domain.step.dto.CreateStepRequest;
import com.caaasperr.Alcoholic.domain.step.dto.UpdateStepRequest;
import com.caaasperr.Alcoholic.domain.step.service.StepService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/steps")
public class StepController implements StepApi {
    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createStep(
            @Valid @ModelAttribute CreateStepRequest request,
            Authentication authentication
    ) throws IOException {
        stepService.createStep(request, authentication);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateStep(
            @PathVariable Long id,
            @Valid @ModelAttribute UpdateStepRequest request,
            Authentication authentication
    ) throws IOException {
        stepService.updateStep(id, request, authentication);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(
            @PathVariable Long id,
            Authentication authentication
    ) {
        stepService.deleteStep(id, authentication);

        return ResponseEntity.noContent().build();
    }
}
