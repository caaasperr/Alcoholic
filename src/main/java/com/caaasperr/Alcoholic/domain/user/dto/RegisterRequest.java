package com.caaasperr.Alcoholic.domain.user.dto;

import com.caaasperr.Alcoholic.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record RegisterRequest(
        @Schema(description = "이메일", example = "test@example.com", requiredMode = REQUIRED)
        @NotNull
        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        @Schema(description = "사용자 이름", example = "caaasperr", requiredMode = REQUIRED)
        @NotNull
        @NotBlank
        @Size(max = 30)
        String username,

        @Schema(description = "비밀번호", example = "pass4cas", requiredMode = REQUIRED)
        @NotNull
        @NotBlank
        @Size(min = 6, max = 255)
        String password
) {
    public User toUser(PasswordEncoder encoder) {
        return User.builder()
                .email(email)
                .username(username)
                .password(encoder.encode(password))
                .createdAt(LocalDateTime.now())
                .build();
    }
}
