package com.caaasperr.Alcoholic.domain.step.dto;

import jakarta.validation.constraints.Size;

public record UpdateStepRequest(
        @Size(max = 500)
        String content
) {
}
