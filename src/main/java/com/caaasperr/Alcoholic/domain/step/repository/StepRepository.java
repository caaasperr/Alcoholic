package com.caaasperr.Alcoholic.domain.step.repository;

import com.caaasperr.Alcoholic.domain.step.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findAllByCocktail_IdOrderByOrderAsc(Long cocktailId);
}
