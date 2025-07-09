package com.caaasperr.Alcoholic.domain.user.dto;

import com.caaasperr.Alcoholic.domain.user.model.User;

import java.time.LocalDateTime;

public record GetUserResponse(
        String email,
        String username,
        LocalDateTime created_at
) {
    public static GetUserResponse of(User user) {
        return new GetUserResponse(user.getEmail(), user.getUsername(), user.getCreatedAt());
    }
}
