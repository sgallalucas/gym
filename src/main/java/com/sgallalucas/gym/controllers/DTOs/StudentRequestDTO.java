package com.sgallalucas.gym.controllers.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Student (Request)")
public record StudentRequestDTO(
        UUID id,

        @NotBlank(message = "required field")
        String name,

        @Email
        @NotBlank(message = "required field")
        String email,

        @Past(message = "birthdate must be a past date")
        @NotNull(message = "required field")
        @JsonFormat(pattern = "dd/MM/yyy")
        LocalDate birthDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        @NotBlank(message = "required field")
        String genre,

        @NotBlank(message = "required field")
        String professorId
) {
}
