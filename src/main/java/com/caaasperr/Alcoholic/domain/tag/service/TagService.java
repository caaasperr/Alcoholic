package com.caaasperr.Alcoholic.domain.tag.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.tag.dto.CreateTagRequest;
import com.caaasperr.Alcoholic.domain.tag.dto.GetTagResponse;
import com.caaasperr.Alcoholic.domain.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void createTag(CreateTagRequest request) {
        tagRepository.save(request.toTag());
    }

    public GetTagResponse getTag(Long id) {
        return GetTagResponse.of(tagRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TAG)));
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
