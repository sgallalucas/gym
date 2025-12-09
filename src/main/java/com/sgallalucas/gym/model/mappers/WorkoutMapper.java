package com.sgallalucas.gym.model.mappers;

import com.sgallalucas.gym.model.DTOs.WorkoutRequestDTO;
import com.sgallalucas.gym.model.DTOs.WorkoutResponseDTO;
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

    @Mapping(target = "professor", expression = "java(professorRepository.findById(UUID.fromString(requestDTO.professorId())).orElse(null))")
    @Mapping(target = "student", expression = "java(studentRepository.findById(UUID.fromString(requestDTO.studentId())).orElse(null))")
    public abstract Workout toEntity(WorkoutRequestDTO requestDTO);

    public abstract WorkoutResponseDTO toDTO(Workout entity);
}
