package com.caaasperr.Alcoholic.domain.tag.controller;

import com.caaasperr.Alcoholic.domain.tag.dto.CreateTagRequest;
import com.caaasperr.Alcoholic.domain.tag.dto.GetTagResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Tag", description = "Tag API")
public interface TagApi {
    @ApiResponse(responseCode = "201")
    @Operation(summary = "태그 생성")
    ResponseEntity<Void> createTag(
            @RequestBody CreateTagRequest request
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "태그 조회")
    ResponseEntity<GetTagResponse> getTag(
            @PathVariable Long id
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "태그 삭제")
    ResponseEntity<Void> deleteTag(
            @PathVariable Long id
    );
}
