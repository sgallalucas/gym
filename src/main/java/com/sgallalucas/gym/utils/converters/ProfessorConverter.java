package com.sgallalucas.gym.utils.converters;

import com.sgallalucas.gym.model.DTOs.ProfessorRequestDTO;
import com.sgallalucas.gym.model.DTOs.ProfessorResponseDTO;
import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.model.enums.Genre;
import com.sgallalucas.gym.model.enums.Specialty;
import org.springframework.stereotype.Component;

@Component
public class ProfessorConverter {

    public Professor toEntity(ProfessorRequestDTO requestDTO) {
        Professor professor = new Professor();
        professor.setId(requestDTO.id());
        professor.setName(requestDTO.name());
        professor.setEmail(requestDTO.email());
        professor.setBirthDate(requestDTO.birthDate());
        professor.setGenre(Genre.valueOf(requestDTO.genre().toUpperCase()));
        professor.setSpecialty(Specialty.valueOf(requestDTO.specialty().toUpperCase()));
        return professor;
    }

    public ProfessorResponseDTO toDTO(Professor professor) {
        return new ProfessorResponseDTO(
                professor.getId(),
                professor.getName(),
                professor.getEmail(),
                professor.getBirthDate(),
                professor.getGenre(),
                professor.getSpecialty(),
                professor.getStudents()
        );
    }
}
