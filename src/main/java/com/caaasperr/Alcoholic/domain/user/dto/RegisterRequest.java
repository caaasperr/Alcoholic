package com.caaasperr.Alcoholic.domain.user.dto;

import com.caaasperr.Alcoholic.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record RegisterRequest(
        @Schema(description = "이메일", example = "test@example.com", requiredMode = REQUIRED)
        @NotNull
        String email,

        @Schema(description = "사용자 이름", example = "caaasperr", requiredMode = REQUIRED)
        @NotNull
        String username,

        @Schema(description = "비밀번호", example = "pass4cas", requiredMode = REQUIRED)
        @NotNull
        String password
) {
    public User toUser() {
        return User.builder().email(email).username(username).password(password).build();
    }
}
