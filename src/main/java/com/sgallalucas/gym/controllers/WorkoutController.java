package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.controllers.DTOs.WorkoutRequestDTO;
import com.sgallalucas.gym.controllers.DTOs.WorkoutResponseDTO;
import com.sgallalucas.gym.controllers.mappers.WorkoutMapper;
import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.services.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
@Tag(name = "Workouts")
public class WorkoutController {

    private final WorkoutService workoutService;
    private final WorkoutMapper workoutMapper;

    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    @Operation(summary = "Create", description = "Register a workout by passing fields name, description, type, student id and professor id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Workout succesfully registered."),
            @ApiResponse(responseCode = "422", description = "Workout with invalid data."),
            @ApiResponse(responseCode = "409", description = "Workout already exists."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> create(@RequestBody @Valid WorkoutRequestDTO requestDTO) {
        Workout workout = workoutMapper.toEntity(requestDTO);
        workoutService.create(workout);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + workout.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROFESSOR', 'STUDENT')")
    @Operation(summary = "Get", description = "Find a Workout by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout successfully found."),
            @ApiResponse(responseCode = "404", description = "Workout not found."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<WorkoutResponseDTO> getWorkout(@PathVariable String id) {
        WorkoutResponseDTO responseDTO = workoutMapper.toDTO(workoutService.getWorkout(UUID.fromString(id)));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    @Operation(summary = "Update", description = "Updates a Workout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Workout successfully updated."),
            @ApiResponse(responseCode = "404", description = "Workout not found."),
            @ApiResponse(responseCode = "422", description = "Invalid data for workout update."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid WorkoutRequestDTO requestDTO) {
        workoutService.update(UUID.fromString(id), workoutMapper.toEntity(requestDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    @Operation(summary = "Delete", description = "Delete a Workout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Workout successfully deleted."),
            @ApiResponse(responseCode = "404", description = "Workout not found."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        workoutService.delete(workoutService.getWorkout(UUID.fromString(id)));
        return ResponseEntity.noContent().build();
    }
}
