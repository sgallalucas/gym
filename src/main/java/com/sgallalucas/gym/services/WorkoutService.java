package com.sgallalucas.gym.services;

import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.repositories.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public void create(Workout workout) {
        workoutRepository.save(workout);
    }

    public Workout getWorkout(UUID id) {
        return workoutRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Workout not found"));
    }

    public void update(UUID id, Workout workout) {
        Workout found = getWorkout(id);
        found.setName(workout.getName());
        found.setDescription(workout.getDescription());
        found.setType(workout.getType());
        found.setProfessor(workout.getProfessor());
        found.setStudent(workout.getStudent());

        workoutRepository.save(found);
    }

    public void delete(Workout workout) {
        workoutRepository.delete(getWorkout(workout.getId()));
    }
}
