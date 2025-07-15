package com.caaasperr.Alcoholic.domain.cocktail.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.dto.*;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailIngredients;
import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailTags;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailTagsRepository;
import com.caaasperr.Alcoholic.domain.tag.model.Tag;
import com.caaasperr.Alcoholic.domain.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CocktailTagsService {
    private final CocktailTagsRepository cocktailTagsRepository;
    private final CocktailRepository cocktailRepository;
    private final TagRepository tagRepository;

    public CocktailTagsService(CocktailTagsRepository cocktailTagsRepository, CocktailRepository cocktailRepository, TagRepository tagRepository) {
        this.cocktailTagsRepository = cocktailTagsRepository;
        this.cocktailRepository = cocktailRepository;
        this.tagRepository = tagRepository;
    }

    public void addTagsToCocktail(Long cocktail_id, AddCocktailTagsRequest request) {
        Cocktail cocktail = cocktailRepository.findById(cocktail_id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));

        for (Long tagId : request.tagIds()) {
            Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TAG));

            cocktailTagsRepository.save(CocktailTags.builder().cocktail(cocktail).tag(tag).build());
        }
    }

    public void removeCocktailTags(Long cocktail_id, RemoveCocktailTagsRequest request) {
        Cocktail cocktail = cocktailRepository.findById(cocktail_id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));

        List<CocktailTags> tagsToRemove = cocktailTagsRepository.findAllById(request.tagIds());

        if (tagsToRemove.size() != request.tagIds().size()) {
            throw new CustomException(ErrorCode.NOT_FOUND_INGREDIENT);
        }

        cocktail.getTags().removeAll(tagsToRemove);
    }

    public List<CocktailTag> getCocktailTags(Long cocktailId) {
        List<CocktailTags> cocktailTags = cocktailTagsRepository.findAllByCocktail_id(cocktailId);
        return cocktailTags.stream()
                .map(CocktailTag::from)
                .collect(Collectors.toList());
    }
}
