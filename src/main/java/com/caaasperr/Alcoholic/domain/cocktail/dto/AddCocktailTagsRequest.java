package com.caaasperr.Alcoholic.domain.cocktail.dto;

import java.util.List;

public record AddCocktailTagsRequest(
        List<Long> tagIds
) {
}
