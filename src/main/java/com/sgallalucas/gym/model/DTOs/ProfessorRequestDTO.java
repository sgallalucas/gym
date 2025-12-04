package com.sgallalucas.gym.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public record ProfessorRequestDTO(
        UUID id,

        @NotBlank(message = "required field")
        String name,

        @Email
        @NotBlank(message = "required field")
        String email,

        @Past
        @NotNull(message = "required field")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate birthDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        @NotNull(message = "required field")
        String genre,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        @NotNull(message = "required field")
        String specialty
) {
}
