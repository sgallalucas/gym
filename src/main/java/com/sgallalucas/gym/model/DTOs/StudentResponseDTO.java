package com.sgallalucas.gym.model.DTOs;

import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.model.enums.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record StudentResponseDTO(UUID id, String name, String email, LocalDate birthDate, Genre genre, List<Workout> workouts) {
}
