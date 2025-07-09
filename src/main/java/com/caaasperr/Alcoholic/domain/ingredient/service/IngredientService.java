package com.caaasperr.Alcoholic.domain.ingredient.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.ingredient.dto.CreateIngredientRequest;
import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import com.caaasperr.Alcoholic.domain.ingredient.repository.IngredientRepository;
import com.caaasperr.Alcoholic.domain.maker.repository.MakerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final MakerRepository makerRepository;

    public IngredientService(IngredientRepository ingredientRepository, MakerRepository makerRepository) {
        this.ingredientRepository = ingredientRepository;
        this.makerRepository = makerRepository;
    }

    public void createIngredient(CreateIngredientRequest request) {
        Ingredient ingredient = request.toIngredient(makerRepository.findById(request.maker_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MAKER)));
        ingredientRepository.save(ingredient);
    }

    public Page<Ingredient> getIngredients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ingredientRepository.findAll(pageable);
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
