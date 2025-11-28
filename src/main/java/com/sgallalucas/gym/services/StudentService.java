package com.sgallalucas.gym.services;

import com.sgallalucas.gym.model.DTOs.StudentRequestDTO;
import com.sgallalucas.gym.model.DTOs.StudentResponseDTO;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public void create(Student student) {
        studentRepository.save(student);
    }

    public Student getStudent(UUID id) {
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public void update(UUID id, Student student) {
        Student found = getStudent(id);
        found.setName(student.getName());
        found.setEmail(student.getEmail());
        found.setBirthDate(student.getBirthDate());
        found.setGenre(student.getGenre());
        studentRepository.save(found);
    }

    public void delete(Student student) {
        Student found = getStudent(student.getId());
        studentRepository.delete(found);
    }

    public Student toEntity(StudentRequestDTO requestDTO) {
        Student student = new Student();
        student.setId(requestDTO.id());
        student.setName(requestDTO.name());
        student.setEmail(requestDTO.email());
        student.setBirthDate(requestDTO.birthDate());
        student.setGenre(requestDTO.genre());
        return student;
    }

    public StudentResponseDTO toDTO(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getBirthDate(),
                student.getGenre(),
                student.getWorkouts()
        );
    }
}
