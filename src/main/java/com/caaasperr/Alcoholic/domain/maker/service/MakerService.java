package com.caaasperr.Alcoholic.domain.maker.service;

import com.caaasperr.Alcoholic._common.dto.Criteria;
import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.ingredient.repository.IngredientRepository;
import com.caaasperr.Alcoholic.domain.maker.dto.CreateMakerRequest;
import com.caaasperr.Alcoholic.domain.maker.dto.GetMakerResponse;
import com.caaasperr.Alcoholic.domain.maker.dto.GetMakersResponse;
import com.caaasperr.Alcoholic.domain.maker.dto.UpdateMakerRequest;
import com.caaasperr.Alcoholic.domain.maker.model.Maker;
import com.caaasperr.Alcoholic.domain.maker.repository.MakerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MakerService {
    private final MakerRepository makerRepository;
    private final IngredientRepository ingredientRepository;

    public MakerService(MakerRepository makerRepository, IngredientRepository ingredientRepository) {
        this.makerRepository = makerRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    public void createMaker(CreateMakerRequest request) {
        makerRepository.save(request.toMaker());
    }

    public GetMakerResponse getMaker(Long id) {
        return GetMakerResponse.of(makerRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MAKER)));
    }

    public GetMakersResponse getMakers(Integer page, Integer size) {
        Criteria criteria = Criteria.of(page, size);
        PageRequest pageRequest = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<Maker> makers = makerRepository.findAll(pageRequest);

        return GetMakersResponse.of(makers, criteria);
    }

    @Transactional
    public void updateMaker(Long id, UpdateMakerRequest request) {
        Maker maker = makerRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MAKER));

        if (request.name() != null && !maker.getName().equals(request.name())) {
            maker.updateName(request.name());
        }

        if (request.country() != null && !maker.getCountry().equals(request.country())) {
            maker.updateCountry(request.country());
        }

        if (request.type() != null && !maker.getType().equals(request.type())) {
            maker.updateType(request.type());
        }

        if (request.description() != null && !maker.getDescription().equals(request.description())) {
            maker.updateDescription(request.name());
        }
    }

    @Transactional
    public void deleteMaker(Long id) {
        ingredientRepository.deleteByMaker_Id(id);
        makerRepository.deleteById(id);
    }
}
