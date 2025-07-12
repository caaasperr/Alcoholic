package com.caaasperr.Alcoholic.domain.cocktail.service;

import com.caaasperr.Alcoholic._common.dto.Criteria;
import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CreateCocktailRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.GetCocktailsResponse;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.step.model.Step;
import com.caaasperr.Alcoholic.domain.step.repository.StepRepository;
import com.caaasperr.Alcoholic.domain.user.repository.UserRepository;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final UserRepository userRepository;

    public CocktailService(CocktailRepository cocktailRepository, UserRepository userRepository) {
        this.cocktailRepository = cocktailRepository;
        this.userRepository = userRepository;
    }

    public void createCocktail(CreateCocktailRequest request) {
        cocktailRepository.save(request.toCocktail(userRepository.findById(request.user_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER))));
    }

    public GetCocktailsResponse getCocktails(
            Integer page, Integer size, boolean match,
            List<String> tags, List<String> ingredients
    ) {
        long total;

        if ((tags == null || tags.isEmpty()) && (ingredients == null || ingredients.isEmpty())) {
            total = cocktailRepository.count();
        } else if (match) {
            List<Long> result = cocktailRepository.countByAllTagsAndAllIngredients(
                    tags != null ? tags : Collections.emptyList(),
                    ingredients != null ? ingredients : Collections.emptyList(),
                    tags != null ? tags.size() : 0,
                    ingredients != null ? ingredients.size() : 0
            );
            total = result.size();
        } else {
            total = cocktailRepository.countByAnyTagsOrAnyIngredients(
                    tags != null ? tags : Collections.emptyList(),
                    ingredients != null ? ingredients : Collections.emptyList()
            );
        }

        Criteria criteria = Criteria.of(page, size, (int) total);
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());

        Page<Cocktail> cocktailPage;

        boolean hasTags = tags != null && !tags.isEmpty();
        boolean hasIngredients = ingredients != null && !ingredients.isEmpty();

        if (match) {
            if (hasTags && hasIngredients) {
                int tagCount = tags.size();
                int ingredientCount = ingredients.size();
                cocktailPage = cocktailRepository.findByAllTagsAndAllIngredientsNative(tags, ingredients, tagCount, ingredientCount, pageable);
            } else if (hasTags) {
                int tagCount = tags.size();
                cocktailPage = cocktailRepository.findByAllTagsOnly(tags, tagCount, pageable);
            } else if (hasIngredients) {
                int ingredientCount = ingredients.size();
                cocktailPage = cocktailRepository.findByAllIngredientsOnly(ingredients, ingredientCount, pageable);
            } else {
                cocktailPage = cocktailRepository.findAll(pageable);
            }
        } else {
            cocktailPage = cocktailRepository.findByAnyTagsOrAnyIngredients(tags, ingredients, pageable);
        }

        return GetCocktailsResponse.of(cocktailPage, criteria);
    }

    public Cocktail getCocktail(Long id) {
        return cocktailRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));
    }

    public void deleteCocktail(Long id) {
        cocktailRepository.deleteById(id);
    }
}
