package com.caaasperr.Alcoholic.domain.cocktail.dto;

import com.caaasperr.Alcoholic._common.dto.Criteria;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.step.model.Step;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public record GetCocktailsResponse(
        List<CocktailRespForGetCocktails> cocktails,
        int current_page,
        int total_page,
        int page_count,
        long total_count
) {

    public static GetCocktailsResponse of(Page<Cocktail> cocktails, Criteria criteria) {
        return new GetCocktailsResponse(
                cocktails.stream().map(CocktailRespForGetCocktails::of).toList(),
                criteria.getPage(),
                cocktails.getTotalPages(),
                cocktails.getContent().size(),
                cocktails.getTotalElements()
        );
    }

    private record CocktailRespForGetCocktails(
            Long id,
            String username,
            String name,
            String description,
            String cover_image,
            List<CocktailTag> tags,
            List<CocktailIngredient> ingredients,
            Float vol,
            Long view,
            LocalDateTime created_at
    ) {
        public static CocktailRespForGetCocktails of(Cocktail cocktail) {
            List<CocktailTag> tags = cocktail.getTags().stream().map(CocktailTag::from).toList();
            List<CocktailIngredient> ingredients = cocktail.getIngredients().stream().map(CocktailIngredient::from).toList();

            return new CocktailRespForGetCocktails(
                    cocktail.getId(),
                    cocktail.getUser().getUsername(),
                    cocktail.getName(),
                    cocktail.getDescription(),
                    cocktail.getCover_image(),
                    tags,
                    ingredients,
                    cocktail.getVol(),
                    cocktail.getView(),
                    cocktail.getCreated_at()
            );
        }
    }
}
