package com.caaasperr.Alcoholic.domain.tag.dto;

import com.caaasperr.Alcoholic.domain.tag.model.Tag;

import java.util.List;

public record GetAllTagResponse(
        List<TagRespForGetAllTag> tags
) {
    public static GetAllTagResponse from(List<Tag> tags) {
        return new GetAllTagResponse(
                tags.stream().map(TagRespForGetAllTag::of).toList()
        );
    }

    private record TagRespForGetAllTag(
            Long id,
            String name,
            String description
    ) {
        public static TagRespForGetAllTag of(Tag tag) {
            return new TagRespForGetAllTag(tag.getId(), tag.getName(), tag.getDescription());
        }
    }
}
