package com.sgallalucas.gym.services;

import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.model.enums.Genre;
import com.sgallalucas.gym.model.enums.Specialty;
import com.sgallalucas.gym.model.enums.Type;
import com.sgallalucas.gym.repositories.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceTest {

    @InjectMocks
    WorkoutService workoutService;

    @Mock
    WorkoutRepository workoutRepository;

    Workout workout, invalidWorkout, updatedWorkout;

    Professor professor;

    Student student;

    @BeforeEach
    void setUp() {
        workout = new Workout(UUID.randomUUID(), "treino", "descrição", Type.FUNCTIONAL, professor, student);
        invalidWorkout = new Workout(null, null, null, null, null, null);
        updatedWorkout = new Workout(workout.getId(), "treino atualizado", "descrição atualizada", Type.FUNCTIONAL, professor, student);
        professor = new Professor(UUID.randomUUID(), "Jorge", "jorge@gmail.com", LocalDate.of(2000, 1, 1), Genre.MALE, Specialty.BODYBUILDING);
        student = new Student(UUID.randomUUID(), "Lucas", "lucas@gmail.com", LocalDate.of(2000, 1, 1), Genre.MALE, professor, List.of(workout));
    }

    @Test
    @DisplayName(value = "create workout with valid data")
    void create() {
        when(workoutRepository.save(workout)).thenReturn(workout);

        workoutService.create(workout);

        verify(workoutRepository, times(1)).save(workout);
    }

    @Test
    @DisplayName(value = "create workout with invalid data")
    void createInvalid() {
        when(workoutRepository.save(invalidWorkout)).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> workoutService.create(invalidWorkout)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "create workout with non-existing professor")
    void createNonExistingProfessor() {
        when(workoutRepository.save(invalidWorkout)).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> workoutService.create(invalidWorkout)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "create workout with non-existing student")
    void createNonExistingStudent() {
        when(workoutRepository.save(invalidWorkout)).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> workoutService.create(invalidWorkout)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "find workout by existent id")
    void getWorkout() {
        when(workoutRepository.findById(workout.getId())).thenReturn(Optional.of(workout));

        Workout sut = workoutService.getWorkout(workout.getId());

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(workout);
    }

    @Test
    @DisplayName(value = "find workout by non-existing id")
    void getNonExistingId() {
        when(workoutRepository.findById(workout.getId())).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> workoutService.getWorkout(workout.getId())).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "find workour by invalid id")
    void getInvalidId() {
        when(workoutRepository.findById(invalidWorkout.getId())).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> workoutService.getWorkout(invalidWorkout.getId())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "update workout with valid data")
    void update() {
        when(workoutRepository.findById(workout.getId())).thenReturn(Optional.of(workout));

        workoutService.update(workout.getId(), updatedWorkout);

        verify(workoutRepository, times(1)).save(workout);
    }

    @Test
    @DisplayName(value = "update workout with invalid data")
    void updateInvalid() {
        when(workoutRepository.findById(workout.getId())).thenReturn(Optional.of(workout));
        doThrow(IllegalArgumentException.class).when(workoutRepository).save(workout);

        assertThatThrownBy(() -> workoutService.update(workout.getId(), invalidWorkout)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "update non-existent workout")
    void updateNonExistent() {
        when(workoutRepository.findById(invalidWorkout.getId())).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> workoutService.update(invalidWorkout.getId(), updatedWorkout)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "update workout with invalid id")
    void updateInvalidId() {
        when(workoutRepository.findById(invalidWorkout.getId())).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> workoutService.delete(invalidWorkout)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "delete existent workout")
    void delete() {
        when(workoutRepository.findById(workout.getId())).thenReturn(Optional.of(workout));

        workoutService.delete(workout);

        verify(workoutRepository, times(1)).delete(workout);
    }

    @Test
    @DisplayName(value = "delete non-existing workout")
    void deleteNonExisting() {
        when(workoutRepository.findById(workout.getId())).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> workoutService.delete(workout)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "delete workout with invalid id")
    void deleteInvalidId() {
        when(workoutRepository.findById(invalidWorkout.getId())).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> workoutService.delete(invalidWorkout)).isInstanceOf(IllegalArgumentException.class);
    }

}
