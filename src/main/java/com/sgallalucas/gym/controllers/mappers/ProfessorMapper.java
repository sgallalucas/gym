package com.sgallalucas.gym.controllers.mappers;

import com.sgallalucas.gym.controllers.DTOs.ProfessorRequestDTO;
import com.sgallalucas.gym.controllers.DTOs.ProfessorResponseDTO;
import com.sgallalucas.gym.model.Professor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

    Professor toEntity(ProfessorRequestDTO professorRequestDTO);

    ProfessorResponseDTO toDTO(Professor professor);
}
