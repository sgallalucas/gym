package com.sgallalucas.gym.controllers.DTOs;

import com.sgallalucas.gym.model.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "Workout (Response)")
public record WorkoutResponseDTO(UUID id, String name, String description, Type type) {
}
