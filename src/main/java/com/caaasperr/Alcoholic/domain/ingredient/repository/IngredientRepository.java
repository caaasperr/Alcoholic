package com.caaasperr.Alcoholic.domain.ingredient.repository;

import com.caaasperr.Alcoholic.domain.ingredient.model.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Page<Ingredient> findAll(Pageable pageable);
}
