package com.caaasperr.Alcoholic.domain.step.controller;

import com.caaasperr.Alcoholic.domain.cocktail.dto.CreateCocktailRequest;
import com.caaasperr.Alcoholic.domain.step.dto.CreateStepRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Step", description = "Step API")
public interface StepApi {
    @ApiResponse(responseCode = "201")
    @Operation(summary = "레시피 단계 생성")
    ResponseEntity<Void> createStep(
            @RequestBody CreateStepRequest request
    );

    @ApiResponse(responseCode = "204")
    @Operation(summary = "레시피 단계 삭제")
    ResponseEntity<Void> deleteStep(
            @PathVariable Long id
    );
}
