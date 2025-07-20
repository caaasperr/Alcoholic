package com.caaasperr.Alcoholic.domain.ingredient.service;

import com.caaasperr.Alcoholic._common.dto.Criteria;
import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailIngredientsRepository;
import com.caaasperr.Alcoholic.domain.ingredient.dto.CreateIngredientRequest;
import com.caaasperr.Alcoholic.domain.ingredient.dto.GetIngredientResponse;
import com.caaasperr.Alcoholic.domain.ingredient.dto.GetIngredientsResponse;
import com.caaasperr.Alcoholic.domain.ingredient.dto.UpdateIngredientRequest;
import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import com.caaasperr.Alcoholic.domain.ingredient.repository.IngredientRepository;
import com.caaasperr.Alcoholic.domain.maker.repository.MakerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final MakerRepository makerRepository;
    private final CocktailIngredientsRepository cocktailIngredientsRepository;

    public IngredientService(IngredientRepository ingredientRepository, MakerRepository makerRepository, CocktailIngredientsRepository cocktailIngredientsRepository) {
        this.ingredientRepository = ingredientRepository;
        this.makerRepository = makerRepository;
        this.cocktailIngredientsRepository = cocktailIngredientsRepository;
    }

    @Transactional
    public void createIngredient(CreateIngredientRequest request) {
        Ingredient ingredient = request.toIngredient(makerRepository.findById(request.maker_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MAKER)));
        ingredientRepository.save(ingredient);
    }

    public GetIngredientResponse getIngredient(Long id) {
        return GetIngredientResponse.of(ingredientRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INGREDIENT)));
    }

    public GetIngredientsResponse getIngredients(Integer page, Integer size) {
        Criteria criteria = Criteria.of(page, size);
        PageRequest pageRequest = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<Ingredient> ingredientPage = ingredientRepository.findAll(pageRequest);

        return GetIngredientsResponse.of(ingredientPage, criteria);
    }

    @Transactional
    public void updateIngredient(Long ingredient_id, UpdateIngredientRequest request) {
        Ingredient ingredient = ingredientRepository.findById(ingredient_id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INGREDIENT));

        if (request.maker_id() != null && !ingredient.getMaker().equals(makerRepository.findById((long) request.maker_id()).orElse(null))) {
            ingredient.updateMaker(makerRepository.findById((long) request.maker_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MAKER)));
        }

        if (request.name() != null && !ingredient.getName().equals(request.name())) {
            ingredient.updateName(request.name());
        }

        if (request.vol() != null && !ingredient.getVol().equals(request.vol())) {
            ingredient.updateVol(request.vol());
        }

        if (request.type() != null && !ingredient.getType().equals(request.type())) {
            ingredient.updateType(request.type());
        }

        if (request.description() != null && !ingredient.getDescription().equals(request.description())) {
            ingredient.updateDescription(request.description());
        }
    }

    @Transactional
    public void deleteIngredient(Long id) {
        cocktailIngredientsRepository.deleteByIngredient_id(id);
        ingredientRepository.deleteById(id);
    }
}
