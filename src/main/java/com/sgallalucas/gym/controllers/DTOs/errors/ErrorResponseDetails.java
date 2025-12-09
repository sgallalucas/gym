package com.sgallalucas.gym.controllers.DTOs.errors;

import java.time.Instant;
import java.util.List;

public record ErrorResponseDetails(
        Integer status,
        String message,
        Instant timestamp,
        List<FieldErrorDetails> errors
) {
}
