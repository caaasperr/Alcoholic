package com.caaasperr.Alcoholic.domain.maker.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.maker.dto.CreateMakerRequest;
import com.caaasperr.Alcoholic.domain.maker.dto.GetMakerResponse;
import com.caaasperr.Alcoholic.domain.maker.model.Maker;
import com.caaasperr.Alcoholic.domain.maker.repository.MakerRepository;
import org.springframework.stereotype.Service;

@Service
public class MakerService {
    private final MakerRepository makerRepository;

    public MakerService(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    public void createMaker(CreateMakerRequest request) {
        makerRepository.save(request.toMaker());
    }

    public GetMakerResponse getMaker(Long id) {
        return GetMakerResponse.of(makerRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MAKER)));
    }

    public void deleteMaker(Long id) {
        makerRepository.deleteById(id);
    }
}
