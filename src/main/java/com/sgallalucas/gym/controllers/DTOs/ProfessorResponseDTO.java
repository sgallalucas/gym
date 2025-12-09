package com.sgallalucas.gym.controllers.DTOs;

import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.model.enums.Genre;
import com.sgallalucas.gym.model.enums.Specialty;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProfessorResponseDTO(UUID id, String name, String email, LocalDate birthDate, Genre genre, Specialty specialty, List<Student> students) {
}
