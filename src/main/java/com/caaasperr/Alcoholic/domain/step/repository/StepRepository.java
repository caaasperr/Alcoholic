package com.caaasperr.Alcoholic.domain.step.repository;

import com.caaasperr.Alcoholic.domain.step.model.Step;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findByCocktail_id(Long cocktailId);

    void deleteByCocktail_Id(Long cocktailId);
}
