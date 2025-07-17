package com.caaasperr.Alcoholic.domain.step.dto;

import java.util.List;

public record ReorderStepRequest(
        List<Long> stepIds
) {
}
