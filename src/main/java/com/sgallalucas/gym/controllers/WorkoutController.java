package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.controllers.DTOs.WorkoutRequestDTO;
import com.sgallalucas.gym.controllers.DTOs.WorkoutResponseDTO;
import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.controllers.mappers.WorkoutMapper;
import com.sgallalucas.gym.services.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;
    private final WorkoutMapper workoutMapper;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid WorkoutRequestDTO requestDTO) {
        Workout workout = workoutMapper.toEntity(requestDTO);
        workoutService.create(workout);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + workout.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResponseDTO> getWorkout(@PathVariable String id) {
        WorkoutResponseDTO responseDTO = workoutMapper.toDTO(workoutService.getWorkout(UUID.fromString(id)));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid WorkoutRequestDTO requestDTO) {
        workoutService.update(UUID.fromString(id), workoutMapper.toEntity(requestDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        workoutService.delete(workoutService.getWorkout(UUID.fromString(id)));
        return ResponseEntity.noContent().build();
    }
}
