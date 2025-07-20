package com.caaasperr.Alcoholic.domain.tag.controller;

import com.caaasperr.Alcoholic.domain.tag.dto.CreateTagRequest;
import com.caaasperr.Alcoholic.domain.tag.dto.GetAllTagResponse;
import com.caaasperr.Alcoholic.domain.tag.dto.GetTagResponse;
import com.caaasperr.Alcoholic.domain.tag.dto.UpdateTagRequest;
import com.caaasperr.Alcoholic.domain.tag.service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController implements TagApi {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createTag(
            @Valid @RequestBody CreateTagRequest request
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

    @GetMapping
    public ResponseEntity<GetAllTagResponse> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTagRequest request
    ) {
        tagService.updateTag(id, request);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable Long id
    ) {
        tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }
}
