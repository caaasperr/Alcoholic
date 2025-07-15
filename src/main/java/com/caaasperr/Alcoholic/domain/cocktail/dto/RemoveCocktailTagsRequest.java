package com.caaasperr.Alcoholic.domain.cocktail.dto;

import java.util.List;

public record RemoveCocktailTagsRequest(
        List<Long> tagIds
) {
}
