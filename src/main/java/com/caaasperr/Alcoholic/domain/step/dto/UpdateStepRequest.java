package com.caaasperr.Alcoholic.domain.step.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record UpdateStepRequest(
        @Size(max = 500)
        String content,

        @Schema(description = "사진")
        MultipartFile image
) {
}
