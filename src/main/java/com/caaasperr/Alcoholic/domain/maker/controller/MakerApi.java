package com.caaasperr.Alcoholic.domain.maker.controller;

import com.caaasperr.Alcoholic.domain.maker.dto.CreateMakerRequest;
import com.caaasperr.Alcoholic.domain.maker.dto.GetMakerResponse;
import com.caaasperr.Alcoholic.domain.maker.dto.UpdateMakerRequest;
import com.caaasperr.Alcoholic.domain.user.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Maker", description = "Maker API")
public interface MakerApi {
    @ApiResponse(responseCode = "201")
    @Operation(summary = "제조사 생성")
    ResponseEntity<Void> createMaker(
            @RequestBody CreateMakerRequest request
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "제조사 조회")
    ResponseEntity<GetMakerResponse> getMaker(
            @PathVariable Long id
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "제조사 수정")
    ResponseEntity<GetMakerResponse> updateMaker(
            @PathVariable Long id,
            @RequestBody UpdateMakerRequest request
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "제조사 삭제")
    ResponseEntity<Void> deleteMaker(
            @PathVariable Long id
    );
}
