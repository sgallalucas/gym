package com.sgallalucas.gym.util.converters;

import com.sgallalucas.gym.model.DTOs.ProfessorRequestDTO;
import com.sgallalucas.gym.model.DTOs.ProfessorResponseDTO;
import com.sgallalucas.gym.model.Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorConverter {

    public Professor toEntity(ProfessorRequestDTO requestDTO) {
        Professor professor = new Professor();
        professor.setId(requestDTO.id());
        professor.setName(requestDTO.name());
        professor.setEmail(requestDTO.email());
        professor.setBirthDate(requestDTO.birthDate());
        professor.setGenre(requestDTO.genre());
        professor.setSpecialty(requestDTO.specialty());
        return professor;
    }

    public ProfessorResponseDTO toDTO(Professor professor) {
        return new ProfessorResponseDTO(
                professor.getId(),
                professor.getName(),
                professor.getEmail(),
                professor.getBirthDate(),
                professor.getGenre(),
                professor.getSpecialty()
        );
    }
}
