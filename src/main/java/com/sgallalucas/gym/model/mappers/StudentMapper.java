package com.sgallalucas.gym.model.mappers;

import com.sgallalucas.gym.model.DTOs.StudentRequestDTO;
import com.sgallalucas.gym.model.DTOs.StudentResponseDTO;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.repositories.ProfessorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class StudentMapper {

    @Autowired
    ProfessorRepository professorRepository;

    @Mapping(target = "professor", expression = "java(professorRepository.findById(UUID.fromString(requestDTO.professorId())).orElse(null))")
    public abstract Student toEntity(StudentRequestDTO requestDTO);

    public abstract StudentResponseDTO toDTO(Student entity);
}
