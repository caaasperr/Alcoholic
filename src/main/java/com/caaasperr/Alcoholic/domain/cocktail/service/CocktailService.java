package com.caaasperr.Alcoholic.domain.cocktail.service;

import com.caaasperr.Alcoholic._common.dto.Criteria;
import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.dto.CreateCocktailRequest;
import com.caaasperr.Alcoholic.domain.cocktail.dto.GetCocktailsResponse;
import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailRepository;
import com.caaasperr.Alcoholic.domain.step.model.Step;
import com.caaasperr.Alcoholic.domain.step.repository.StepRepository;
import com.caaasperr.Alcoholic.domain.user.repository.UserRepository;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final UserRepository userRepository;

    public CocktailService(CocktailRepository cocktailRepository, UserRepository userRepository) {
        this.cocktailRepository = cocktailRepository;
        this.userRepository = userRepository;
    }

    public void createCocktail(CreateCocktailRequest request) {
        cocktailRepository.save(request.toCocktail(userRepository.findById(request.user_id()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER))));
    }

    public GetCocktailsResponse getCocktails(Integer page, Integer size) {
        long total = cocktailRepository.count();
        Criteria criteria = Criteria.of(page, size, Long.valueOf(total).intValue());
        PageRequest pageRequest = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<Cocktail> cocktailPage = cocktailRepository.findAll(pageRequest);

        return GetCocktailsResponse.of(cocktailPage, criteria);
    }

    public Cocktail getCocktail(Long id) {
        return cocktailRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COCKTAIL));
    }

    public void deleteCocktail(Long id) {
        cocktailRepository.deleteById(id);
    }
}
