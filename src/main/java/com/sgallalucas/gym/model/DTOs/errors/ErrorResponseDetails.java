package com.sgallalucas.gym.model.DTOs.errors;

import java.time.Instant;
import java.util.List;

public record ErrorResponseDetails(
        Integer status,
        String message,
        Instant timestamp,
        List<FieldErrorDetails> errors
) {
}
