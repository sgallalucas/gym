package com.sgallalucas.gym.controllers.mappers;

import com.sgallalucas.gym.controllers.DTOs.WorkoutRequestDTO;
import com.sgallalucas.gym.controllers.DTOs.WorkoutResponseDTO;
import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.repositories.ProfessorRepository;
import com.sgallalucas.gym.repositories.StudentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class WorkoutMapper {

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    StudentRepository studentRepository;

    @Mapping(target = "professor", expression = "java(professorRepository.findById(UUID.fromString(requestDTO.professorId())).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(\"Professor not found\")))")
    @Mapping(target = "student", expression = "java(studentRepository.findById(UUID.fromString(requestDTO.studentId())).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(\"Student not found\")))")
    public abstract Workout toEntity(WorkoutRequestDTO requestDTO);

    public abstract WorkoutResponseDTO toDTO(Workout entity);
}
