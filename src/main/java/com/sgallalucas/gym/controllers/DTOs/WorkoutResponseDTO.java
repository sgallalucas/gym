package com.sgallalucas.gym.controllers.DTOs;

import com.sgallalucas.gym.model.enums.Type;

import java.util.UUID;

public record WorkoutResponseDTO(UUID id, String name, String description, Type type) {
}
