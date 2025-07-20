package com.caaasperr.Alcoholic.domain.maker.dto;

import com.caaasperr.Alcoholic.domain.maker.model.Maker;

public record GetMakerResponse(
        Long maker_id,
        String name,
        String country,
        String type,
        String description
) {
    public static GetMakerResponse of(Maker maker) {
        return new GetMakerResponse(maker.getId(), maker.getName(), maker.getCountry(), maker.getType(), maker.getDescription());
    }
}
