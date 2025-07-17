package com.caaasperr.Alcoholic.domain.tag.dto;

import jakarta.validation.constraints.Size;

public record UpdateTagRequest(
        @Size(max = 50)
        String name,

        @Size(max = 500)
        String description
) {
}
