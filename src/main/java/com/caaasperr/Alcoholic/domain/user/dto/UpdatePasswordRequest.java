package com.caaasperr.Alcoholic.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record UpdatePasswordRequest(
        @Schema(description = "비밀번호", example = "pass4cas", requiredMode = REQUIRED)
        @NotNull
        @NotBlank
        @Size(min = 6, max = 255)
        String password
) {
}
