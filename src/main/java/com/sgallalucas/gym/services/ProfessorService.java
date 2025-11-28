package com.sgallalucas.gym.services;

import com.sgallalucas.gym.model.DTOs.ProfessorRequestDTO;
import com.sgallalucas.gym.model.DTOs.ProfessorResponseDTO;
import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.repositories.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public void create(Professor professor) {
        professorRepository.save(professor);
    }

    public Professor getProfessor(UUID id) {
        return professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found"));
    }

    public void update(UUID id, Professor professor) {
        Professor found = getProfessor(id);
        found.setName(professor.getName());
        found.setEmail(professor.getEmail());
        found.setBirthDate(professor.getBirthDate());
        found.setGenre(professor.getGenre());
        found.setSpecialty(professor.getSpecialty());
        professorRepository.save(found);
    }

    public void delete(Professor professor) {
        Professor found = getProfessor(professor.getId());
        professorRepository.delete(found);
    }

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
                professor.getSpecialty(),
                professor.getStudents()
        );
    }
}
