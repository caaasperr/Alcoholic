package com.caaasperr.Alcoholic.domain.cocktail.repository;

import com.caaasperr.Alcoholic.domain.cocktail.model.CocktailTags;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailTagsRepository extends JpaRepository<CocktailTags, Long> {
    @EntityGraph(attributePaths = {"tag"})
    List<CocktailTags> findAllByCocktail_id(Long cocktailId);

    void deleteByTag_Id(Long tagId);

    void deleteByCocktail_Id(Long cocktailId);
}