package com.caaasperr.Alcoholic.domain.cocktail.service;

import com.caaasperr.Alcoholic._common.annotation.CheckCocktailOwner;
import com.caaasperr.Alcoholic._common.dto.Criteria;
import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic._common.image.ImageHandler;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CreateCocktailRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.GetCocktailResponse;
import com.caaasperr.Alcoholic.domain.cocktail.dto.GetCocktailsResponse;
import com.caaasperr.Alcoholic.domain.cocktail.dto.UpdateCocktailRequest;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final UserRepository userRepository;
    private final ImageHandler imageHandler;

    public CocktailService(CocktailRepository cocktailRepository, UserRepository userRepository, ImageHandler imageHandler) {
        this.cocktailRepository = cocktailRepository;
        this.userRepository = userRepository;
        this.imageHandler = imageHandler;
    }

    public void createCocktail(CreateCocktailRequest request) throws IOException {
        String imagePath = request.cover_image() != null ? imageHandler.saveImage(request.cover_image()) : null;
        cocktailRepository.save(
                request.toCocktail(
                        userRepository.findById(request.user_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)),
                        imagePath
                )
        );
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
            if (hasTags || hasIngredients) {
                cocktailPage = cocktailRepository.findByAnyTagsOrAnyIngredients(tags, ingredients, pageable);
            } else {
                cocktailPage = cocktailRepository.findAll(pageable);
            }
        }

        return GetCocktailsResponse.of(cocktailPage, criteria);
    }

    public Cocktail getCocktail(Long id) {
        return cocktailRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));
    }

    public GetCocktailsResponse searchByName(String query, Integer page, Integer size) {
        if (query == null || query.trim().isEmpty()) {
            return new GetCocktailsResponse(List.of(), page, size, 0, 0);
        }

        long total = cocktailRepository.countByNameContaining(query);
        Criteria criteria = Criteria.of(page, size, (int) total);
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());

        Page<Cocktail> cocktailPage = cocktailRepository.findByNameContaining(query, pageable);
        return GetCocktailsResponse.of(cocktailPage, criteria);
    }

    @CheckCocktailOwner
    @Transactional
    public void updateCocktail(Long id, UpdateCocktailRequest request, Authentication authentication) throws IOException {
        Cocktail cocktail = cocktailRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));

        if (request.name() != null && !cocktail.getName().equals(request.name())) {
            cocktail.updateName(request.name());
        }

        if (request.description() != null && !cocktail.getDescription().equals(request.description())) {
            cocktail.updateDescription(request.description());
        }

        MultipartFile newImageFile = request.cover_image();

        if (newImageFile != null && !newImageFile.isEmpty()) {
            String newFilename = newImageFile.getOriginalFilename();
            String expectedPath = "/uploads/" + newFilename;
            String oldImage = cocktail.getCover_image();

            if (oldImage == null || !oldImage.equals(expectedPath)) {
                String imagePath = imageHandler.saveImage(newImageFile);
                cocktail.updateCover_image(imagePath);
            }
        }

        if (request.vol() != null && !cocktail.getVol().equals(request.vol())) {
            cocktail.updateVol(request.vol());
        }

        cocktail.updateUpdated_at(LocalDateTime.now());
    }

    @CheckCocktailOwner
    public void deleteCocktail(Long id, Authentication authentication) {
        cocktailRepository.deleteById(id);
    }
}
