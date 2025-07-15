package com.caaasperr.Alcoholic.domain.maker.dto;

public record UpdateMakerRequest(
        String name,
        String country,
        String type,
        String description
) {
}
