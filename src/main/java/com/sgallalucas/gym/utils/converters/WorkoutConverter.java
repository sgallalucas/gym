package com.sgallalucas.gym.utils.converters;

import com.sgallalucas.gym.model.DTOs.WorkoutRequestDTO;
import com.sgallalucas.gym.model.DTOs.WorkoutResponseDTO;
import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.model.enums.Type;
import com.sgallalucas.gym.services.ProfessorService;
import com.sgallalucas.gym.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WorkoutConverter {

    private final ProfessorService professorService;
    private final StudentService studentService;

    public Workout toEntity(WorkoutRequestDTO requestDTO) {
        Workout workout = new Workout();
        workout.setId(requestDTO.id());
        workout.setName(requestDTO.name());
        workout.setDescription(requestDTO.description());
        workout.setType(Type.valueOf(requestDTO.type().toUpperCase()));
        workout.setProfessor(professorService.getProfessor(UUID.fromString(requestDTO.professorId())));
        workout.setStudent(studentService.getStudent(UUID.fromString(requestDTO.studentId())));
        return workout;
    }

    public WorkoutResponseDTO toDTO(Workout workout) {
        return new WorkoutResponseDTO(
                workout.getId(),
                workout.getName(),
                workout.getDescription(),
                workout.getType()
        );
    }
}
