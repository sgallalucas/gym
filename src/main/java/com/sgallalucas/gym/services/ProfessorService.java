package com.sgallalucas.gym.services;

import com.sgallalucas.gym.exceptions.NotAllowedOperationException;
import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.repositories.ProfessorRepository;
import com.sgallalucas.gym.validators.ProfessorValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorValidator validator;

    public void create(Professor professor) {
        validator.validation(professor.getEmail());
        professorRepository.save(professor);
    }

    public Professor getProfessor(UUID id) {
        return professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found"));
    }

    public void update(UUID id, Professor professor) {
        validator.validation(professor.getEmail());
        Professor found = professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found"));
        found.setName(professor.getName());
        found.setEmail(professor.getEmail());
        found.setBirthDate(professor.getBirthDate());
        found.setGenre(professor.getGenre());
        found.setSpecialty(professor.getSpecialty());
        professorRepository.save(found);
    }

    public void delete(Professor professor) {
        Professor found = professorRepository.findById(professor.getId()).orElseThrow(() -> new EntityNotFoundException("Professor not found"));
        if (hasStudent(found)) {
            throw new NotAllowedOperationException("It's not allowed delete a professor that has students or workouts");
        }
        professorRepository.delete(found);
    }

    public Page<Professor> findAll(Pageable pageable) {
        return professorRepository.findAll(pageable);
    }

    public boolean hasStudent(Professor professor) {
        return (professor.getStudents().isEmpty()) ? false : true;
    }
}
