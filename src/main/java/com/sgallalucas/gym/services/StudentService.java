package com.sgallalucas.gym.services;

import com.sgallalucas.gym.exceptions.NotAllowedOperationException;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.repositories.StudentRepository;
import com.sgallalucas.gym.validators.StudentValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentValidator validator;

    public void create(Student student) {
        validator.validation(student.getEmail());
        studentRepository.save(student);
    }

    public Student getStudent(UUID id) {
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public void update(UUID id, Student student) {
        validator.validation(student.getEmail());
        Student found = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        found.setName(student.getName());
        found.setEmail(student.getEmail());
        found.setBirthDate(student.getBirthDate());
        found.setGenre(student.getGenre());
        studentRepository.save(found);
    }

    public void delete(Student student) {
        Student found = studentRepository.findById(student.getId()).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        if (hasWorkout(found)) {
            throw new NotAllowedOperationException("It's not allowed delete a student that has workouts");
        }
        studentRepository.delete(found);
    }

    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public boolean hasWorkout(Student student) {
        return (student.getWorkouts() == null) ? false : true;
    }
}
