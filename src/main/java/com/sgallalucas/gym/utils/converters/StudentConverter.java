package com.sgallalucas.gym.util.converters;

import com.sgallalucas.gym.model.DTOs.StudentRequestDTO;
import com.sgallalucas.gym.model.DTOs.StudentResponseDTO;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.model.enums.Genre;
import com.sgallalucas.gym.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StudentConverter {

    private final ProfessorService professorService;

    public Student toEntity(StudentRequestDTO requestDTO) {
        Student student = new Student();
        student.setId(requestDTO.id());
        student.setName(requestDTO.name());
        student.setEmail(requestDTO.email());
        student.setBirthDate(requestDTO.birthDate());
        student.setGenre(Genre.valueOf(requestDTO.genre().toUpperCase()));
        student.setProfessor(professorService.getProfessor(UUID.fromString(requestDTO.professorId())));
        return student;
    }

    public StudentResponseDTO toDTO(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getBirthDate(),
                student.getGenre(),
                student.getProfessor()
        );
    }
}
