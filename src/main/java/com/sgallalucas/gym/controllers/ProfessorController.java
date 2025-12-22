package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.controllers.DTOs.ProfessorRequestDTO;
import com.sgallalucas.gym.controllers.DTOs.ProfessorResponseDTO;
import com.sgallalucas.gym.controllers.mappers.ProfessorMapper;
import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.services.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
@Tag(name = "Professors")
public class ProfessorController {

    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create", description = "Register a new Professor by passing the fields name, email, birth date, genre and specialty.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Professor registered successfully."),
            @ApiResponse(responseCode = "422", description = "Professor with invalid data."),
            @ApiResponse(responseCode = "409", description = "Professor already exists."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> create(@RequestBody @Valid ProfessorRequestDTO requestDTO) {
        Professor professor = professorMapper.toEntity(requestDTO);
        professorService.create(professor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + professor.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get", description = "Find a Professor by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor successfully found."),
            @ApiResponse(responseCode = "404", description = "Professor not found."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ProfessorResponseDTO> getProfessor(@PathVariable String id) {
        ProfessorResponseDTO responseDTO = professorMapper.toDTO(professorService.getProfessor(UUID.fromString(id)));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update", description = "Updates a Professor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor successfully updated."),
            @ApiResponse(responseCode = "404", description = "Professor not found."),
            @ApiResponse(responseCode = "422", description = "Invalid data for Professor update."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid ProfessorRequestDTO requestDTO) {
        professorService.update(UUID.fromString(id), professorMapper.toEntity(requestDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete", description = "Delete Professor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor successfully deleted."),
            @ApiResponse(responseCode = "404", description = "Professor not found"),
            @ApiResponse(responseCode = "200", description = "Not allowed operation. Professors who have student(s) cannot be deleted."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        professorService.delete(professorService.getProfessor(UUID.fromString(id)));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all", description = "Find all Professors.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list with all Professors or a empty list"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Page<ProfessorResponseDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                              @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfessorResponseDTO> responseDTO = professorService.findAll(pageable).map(professorMapper::toDTO);
        return ResponseEntity.ok().body(responseDTO);
    }
}
