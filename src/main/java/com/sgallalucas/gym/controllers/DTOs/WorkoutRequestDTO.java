package com.sgallalucas.gym.controllers.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Schema(name = "Workout (Request)")
public record WorkoutRequestDTO(
        UUID id,

        @NotBlank(message = "required field")
        String name,

        @NotBlank(message = "required field")
        String description,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        @NotBlank(message = "required field")
        String type,

        @NotBlank(message = "required field")
        String professorId,

        @NotBlank(message = "required field")
        String studentId
) {
}
