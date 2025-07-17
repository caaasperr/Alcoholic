package com.caaasperr.Alcoholic.domain.maker.controller;

import com.caaasperr.Alcoholic.domain.maker.dto.CreateMakerRequest;
import com.caaasperr.Alcoholic.domain.maker.dto.GetMakerResponse;
import com.caaasperr.Alcoholic.domain.maker.dto.UpdateMakerRequest;
import com.caaasperr.Alcoholic.domain.maker.model.Maker;
import com.caaasperr.Alcoholic.domain.maker.service.MakerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/makers")
public class MakerController implements MakerApi {
    private final MakerService makerService;

    public MakerController(MakerService makerService) {
        this.makerService = makerService;
    }

    @PostMapping
    public ResponseEntity<Void> createMaker(
            @Valid @RequestBody CreateMakerRequest request
    ) {
        makerService.createMaker(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMakerResponse> getMaker(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(makerService.getMaker(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMaker(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMakerRequest request
    ) {
        makerService.updateMaker(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaker(

            @PathVariable Long id
    ) {
        makerService.deleteMaker(id);

        return ResponseEntity.noContent().build();
    }
}
