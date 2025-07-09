package com.caaasperr.Alcoholic.domain.tag.dto;

import com.caaasperr.Alcoholic.domain.tag.model.Tag;

public record CreateTagRequest(
        String name,
        String description
) {
    public Tag toTag() {
        return Tag.builder()
                .name(name)
                .description(description)
                .build();
    }
}
