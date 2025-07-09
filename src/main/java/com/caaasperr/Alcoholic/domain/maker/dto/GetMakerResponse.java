package com.caaasperr.Alcoholic.domain.maker.dto;

import com.caaasperr.Alcoholic.domain.maker.model.Maker;

public record GetMakerResponse(
        String name,
        String country,
        String type,
        String description
) {
    public static GetMakerResponse of(Maker maker) {
        return new GetMakerResponse(maker.getName(), maker.getCountry(), maker.getType(), maker.getDescription());
    }
}
