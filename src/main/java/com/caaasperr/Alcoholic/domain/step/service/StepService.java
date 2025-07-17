package com.caaasperr.Alcoholic.domain.step.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic._common.exception.IllegalArgumentException;
import com.caaasperr.Alcoholic._common.image.ImageHandler;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.step.dto.CocktailStep;
import com.caaasperr.Alcoholic.domain.step.dto.CreateStepRequest;
import com.caaasperr.Alcoholic.domain.step.dto.UpdateStepRequest;
import com.caaasperr.Alcoholic.domain.step.model.Step;
import com.caaasperr.Alcoholic.domain.step.repository.StepRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StepService {
    private final StepRepository stepRepository;
    private final CocktailRepository cocktailRepository;
    private final ImageHandler imageHandler;

    public StepService(StepRepository stepRepository, CocktailRepository cocktailRepository, ImageHandler imageHandler) {
        this.stepRepository = stepRepository;
        this.cocktailRepository = cocktailRepository;
        this.imageHandler = imageHandler;
    }

    @Transactional
    public void createStep(CreateStepRequest request) throws IOException {
        String imagePath = request.image() != null ? imageHandler.saveImage(request.image()) : null;
        stepRepository.save(
                request.toStep(
                        cocktailRepository.findById(request.cocktail_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL)),
                        imagePath
                )
        );
    }

    public List<CocktailStep> getStepsByCocktailID(Long id) {
        return stepRepository.findByCocktail_id(id).stream().map(CocktailStep::from).toList();
    }

    @Transactional
    public void updateStep(Long id, UpdateStepRequest request) throws IOException {
        Step step = stepRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));

        if (request.content() != null && !step.getContent().equals(request.content())) {
            step.updateContent(request.content());
        }

        MultipartFile newImageFile = request.image();

        if (newImageFile != null && !newImageFile.isEmpty()) {
            String newFilename = newImageFile.getOriginalFilename();
            String expectedPath = "/uploads/" + newFilename;
            String oldImage = step.getImage();

            if (oldImage == null || !oldImage.equals(expectedPath)) {
                String imagePath = imageHandler.saveImage(newImageFile);
                step.updateImage(imagePath);
            }
        }
    }

    @Transactional
    public void reorderSteps(Long cocktailId, List<Long> orderedStepIds) {
        List<Step> steps = stepRepository.findByCocktail_id(cocktailId);

        if (steps.size() != orderedStepIds.size()) {
            throw new IllegalArgumentException("Steps count mismatch.");
        }

        Map<Long, Step> stepMap = steps.stream()
                .collect(Collectors.toMap(Step::getId, Function.identity()));

        for (int i = 0; i < orderedStepIds.size(); i++) {
            Long stepId = orderedStepIds.get(i);
            Step step = stepMap.get(stepId);

            if (step == null) {
                throw new IllegalArgumentException("Invalid step ID: " + stepId);
            }

            step.updateOrder(i + 1);
        }
    }

    public void deleteStep(Long id) {
        stepRepository.deleteById(id);
    }
}
