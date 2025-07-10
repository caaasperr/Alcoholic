package com.caaasperr.Alcoholic.domain.cocktail.repository;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

}
