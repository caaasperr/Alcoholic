package com.caaasperr.Alcoholic.domain.cocktail.repository;

import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailTags;
import com.caaasperr.Alcoholic.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailTagsRepository extends JpaRepository<CocktailTags, Long> {
    List<CocktailTags> findAllByCocktail_id(Long cocktailId);
}