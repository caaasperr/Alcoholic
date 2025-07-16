package com.caaasperr.Alcoholic.domain.step.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.step.dto.CocktailStep;
import com.caaasperr.Alcoholic.domain.step.dto.CreateStepRequest;
import com.caaasperr.Alcoholic.domain.step.dto.UpdateStepRequest;
import com.caaasperr.Alcoholic.domain.step.model.Step;
import com.caaasperr.Alcoholic.domain.step.repository.StepRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void updateStep(Long id, UpdateStepRequest request) {
        Step step = stepRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));

        if (request.content() != null && !step.getContent().equals(request.content())) {
            step.updateContent(request.content());
        }
    }

    public void deleteStep(Long id) {
        stepRepository.deleteById(id);
    }
}
