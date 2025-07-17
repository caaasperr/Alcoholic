package com.caaasperr.Alcoholic.domain.maker.dto;

import jakarta.validation.constraints.Size;

public record UpdateMakerRequest(
        @Size(max = 100)
        String name,

        @Size(max = 50)
        String country,

        @Size(max = 20)
        String type,

        @Size(max = 500)
        String description
) {
}
