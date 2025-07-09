package com.caaasperr.Alcoholic.domain.tag.controller;

import com.caaasperr.Alcoholic.domain.maker.dto.CreateMakerRequest;
import com.caaasperr.Alcoholic.domain.tag.dto.CreateTagRequest;
import com.caaasperr.Alcoholic.domain.tag.dto.GetTagResponse;
import com.caaasperr.Alcoholic.domain.tag.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController implements TagApi {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Void> createTag(
            @RequestBody CreateTagRequest request
    ) {
        tagService.createTag(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTagResponse> getTag(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(tagService.getTag(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable Long id
    ) {
        tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }
}
