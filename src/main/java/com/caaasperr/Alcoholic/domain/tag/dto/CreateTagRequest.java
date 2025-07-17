package com.caaasperr.Alcoholic.domain.tag.dto;

import com.caaasperr.Alcoholic.domain.tag.model.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTagRequest(
        @NotNull
        @NotBlank
        @Size(max = 50)
        String name,

        @Size(max = 500)
        String description
) {
    public Tag toTag() {
        return Tag.builder()
                .name(name)
                .description(description)
                .build();
    }
}
