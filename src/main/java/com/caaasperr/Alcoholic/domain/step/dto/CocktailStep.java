package com.caaasperr.Alcoholic.domain.step.dto;

import com.caaasperr.Alcoholic.domain.step.model.Step;

public record CocktailStep(
        Long stepId,
        int order,
        String image,
        String content
) {
    public static CocktailStep from(Step step) {
        return new CocktailStep(step.getId(), step.getOrder(), step.getImage(), step.getContent());
    }
}
