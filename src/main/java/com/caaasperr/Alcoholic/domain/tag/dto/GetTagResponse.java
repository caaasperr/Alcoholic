package com.caaasperr.Alcoholic.domain.tag.dto;

import com.caaasperr.Alcoholic.domain.tag.model.Tag;

public record GetTagResponse(
        String name,
        String description
) {
    public static GetTagResponse of(Tag tag) {
        return new GetTagResponse(tag.getName(), tag.getDescription());
    }
}
