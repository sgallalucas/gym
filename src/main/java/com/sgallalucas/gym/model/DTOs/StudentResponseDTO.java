package com.sgallalucas.gym.model.DTOs;

import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.model.enums.Genre;

import java.time.LocalDate;
import java.util.UUID;

public record StudentResponseDTO(UUID id, String name, String email, LocalDate birthDate, Genre genre, Professor professor) {
}
