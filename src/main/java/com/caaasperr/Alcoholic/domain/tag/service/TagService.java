package com.caaasperr.Alcoholic.domain.tag.service;

import com.caaasperr.Alcoholic._common.exception.CustomException;
import com.caaasperr.Alcoholic._common.exception.ErrorCode;
import com.caaasperr.Alcoholic.domain.cocktail.repository.CocktailTagsRepository;
import com.caaasperr.Alcoholic.domain.tag.dto.CreateTagRequest;
import com.caaasperr.Alcoholic.domain.tag.dto.GetAllTagResponse;
import com.caaasperr.Alcoholic.domain.tag.dto.GetTagResponse;
import com.caaasperr.Alcoholic.domain.tag.dto.UpdateTagRequest;
import com.caaasperr.Alcoholic.domain.tag.model.Tag;
import com.caaasperr.Alcoholic.domain.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final CocktailTagsRepository cocktailTagsRepository;

    public TagService(TagRepository tagRepository, CocktailTagsRepository cocktailTagsRepository) {
        this.tagRepository = tagRepository;
        this.cocktailTagsRepository = cocktailTagsRepository;
    }

    @Transactional
    public void createTag(CreateTagRequest request) {
        tagRepository.save(request.toTag());
    }

    public GetTagResponse getTag(Long id) {
        return GetTagResponse.of(tagRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TAG)));
    }

    public GetAllTagResponse getAllTags() {
        return GetAllTagResponse.from(tagRepository.findAll());
    }

    @Transactional
    public void updateTag(Long id, UpdateTagRequest request) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TAG));

        if (request.name() != null && !tag.getName().equals(request.name())) {
            tag.updateName(request.name());
        }

        if (request.description() != null && !tag.getDescription().equals(request.description())) {
            tag.updateDescription(request.description());
        }
    }

    @Transactional
    public void deleteTag(Long id) {
        cocktailTagsRepository.deleteByTag_Id(id);
        tagRepository.deleteById(id);
    }
}
