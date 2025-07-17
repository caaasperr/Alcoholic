package com.caaasperr.Alcoholic.domain.maker.dto;

import com.caaasperr.Alcoholic._common.dto.Criteria;
import com.caaasperr.Alcoholic.domain.maker.model.Maker;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetMakersResponse(
        List<GetMakerResponse> makers,
        int current_page,
        int total_page,
        int page_count,
        long total_count
) {
    public static GetMakersResponse of(Page<Maker> makers, Criteria criteria) {
        return new GetMakersResponse(
                makers.stream().map(GetMakerResponse::of).toList(),
                criteria.getPage(),
                makers.getTotalPages(),
                makers.getContent().size(),
                makers.getTotalElements()
        );
    }
}
