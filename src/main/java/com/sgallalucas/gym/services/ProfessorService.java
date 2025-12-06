package com.sgallalucas.gym.services;

import com.sgallalucas.gym.exceptions.NotAllowedOperationException;
import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.repositories.ProfessorRepository;
import com.sgallalucas.gym.validators.ProfessorValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (hasStudentOrWorkout(found)) {
            throw new NotAllowedOperationException("It's not allowed delete a professor that has students or workouts");
        }
        professorRepository.delete(found);
    }

    public boolean hasStudentOrWorkout(Professor professor) {
        List<Student> studentList = professor.getStudents();
        List<Workout> workoutList = professor.getWorkouts();
        return (studentList.isEmpty() || workoutList.isEmpty()) ? false : true;
    }
}
