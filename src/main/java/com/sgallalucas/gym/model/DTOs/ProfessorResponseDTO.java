package com.sgallalucas.gym.model.DTOs;

import com.sgallalucas.gym.model.enums.Genre;
import com.sgallalucas.gym.model.enums.Specialty;

import java.time.LocalDate;
import java.util.UUID;

public record ProfessorResponseDTO(UUID id, String name, String email, LocalDate birthDate, Genre genre, Specialty specialty) {
}
