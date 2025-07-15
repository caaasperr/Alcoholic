package com.caaasperr.Alcoholic.domain.ingredient.dto;

public record UpdateIngredientRequest(
        Integer maker_id,
        String name,
        Float vol,
        String type,
        String description
) {
}
