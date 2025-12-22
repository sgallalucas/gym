package com.sgallalucas.gym.controllers.DTOs;

import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.model.enums.Genre;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Schema(name = "Student (Response)")
public record StudentResponseDTO(UUID id, String name, String email, LocalDate birthDate, Genre genre, List<Workout> workouts) {
}
