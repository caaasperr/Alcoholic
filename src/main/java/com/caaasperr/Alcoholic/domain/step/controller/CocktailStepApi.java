package com.caaasperr.Alcoholic.domain.step.controller;

import com.caaasperr.Alcoholic.domain.step.dto.CocktailStep;
import com.caaasperr.Alcoholic.domain.step.dto.ReorderStepRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Step")
public interface CocktailStepApi {
    @ApiResponse(responseCode = "200")
    @Operation(summary = "칵테일 단계 조회")
    ResponseEntity<List<CocktailStep>> getSteps(
            @PathVariable Long id
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "403"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "칵테일 단계 재배치")
    ResponseEntity<Void> reorderSteps(
            @PathVariable Long id,
            @RequestBody ReorderStepRequest request,
            Authentication authentication
    );
}
