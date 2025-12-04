package com.sgallalucas.gym.model.DTOs;

import com.sgallalucas.gym.model.enums.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record WorkoutRequestDTO(
        UUID id,

        @NotBlank(message = "required field")
        String name,

        @NotBlank(message = "required field")
        String description,

        @NotNull(message = "required field")
        Type type,

        @NotBlank(message = "required field")
        String professorId,

        @NotBlank(message = "required field")
        String studentId
) {
}
