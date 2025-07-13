package com.caaasperr.Alcoholic.domain.step.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.step.dto.CocktailStep;
import com.caaasperr.Alcoholic.domain.step.dto.CreateStepRequest;
import com.caaasperr.Alcoholic.domain.step.model.Step;
import com.caaasperr.Alcoholic.domain.step.repository.StepRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepService {
    private final StepRepository stepRepository;
    private final CocktailRepository cocktailRepository;

    public StepService(StepRepository stepRepository, CocktailRepository cocktailRepository) {
        this.stepRepository = stepRepository;
        this.cocktailRepository = cocktailRepository;
    }

    public void createStep(CreateStepRequest request) {
        stepRepository.save(request.toStep(cocktailRepository.findById(request.cocktail_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL))));
    }

    public List<CocktailStep> getStepsByCocktailID(Long id) {
        return stepRepository.findAllByCocktail_IdOrderByOrderAsc(id).stream().map(CocktailStep::from).toList();
    }

    public void deleteStep(Long id) {
        stepRepository.deleteById(id);
    }
}
