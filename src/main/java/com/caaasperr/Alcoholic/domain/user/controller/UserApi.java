package com.caaasperr.Alcoholic.domain.user.controller;

import com.caaasperr.Alcoholic.domain.user.dto.GetUserResponse;
import com.caaasperr.Alcoholic.domain.user.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "User API")
public interface UserApi {
    @ApiResponse(responseCode = "201")
    @Operation(summary = "회원가입")
    ResponseEntity<Void> register(
            @RequestBody RegisterRequest request
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "유저 정보 가져오기")
    ResponseEntity<GetUserResponse> getUser(
            @PathVariable String username
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204")
            }
    )
    @Operation(summary = "유저 삭제")
    ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    );
}
