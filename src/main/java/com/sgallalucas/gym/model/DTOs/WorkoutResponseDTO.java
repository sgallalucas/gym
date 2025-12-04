package com.sgallalucas.gym.model.DTOs;

import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.model.enums.Type;

import java.util.UUID;

public record WorkoutResponseDTO(UUID id, String name, String description, Type type, Student student) {
}
