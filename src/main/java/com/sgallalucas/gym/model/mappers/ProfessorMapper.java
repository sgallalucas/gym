package com.sgallalucas.gym.model.mappers;

import com.sgallalucas.gym.model.DTOs.ProfessorRequestDTO;
import com.sgallalucas.gym.model.DTOs.ProfessorResponseDTO;
import com.sgallalucas.gym.model.Professor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

    Professor toEntity(ProfessorRequestDTO professorRequestDTO);

    ProfessorResponseDTO toDTO(Professor professor);
}
