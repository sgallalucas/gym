package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.model.DTOs.ProfessorRequestDTO;
import com.sgallalucas.gym.model.DTOs.ProfessorResponseDTO;
import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.services.ProfessorService;
import com.sgallalucas.gym.util.converters.ProfessorConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;
    private final ProfessorConverter professorConverter;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ProfessorRequestDTO requestDTO) {
        Professor professor = professorConverter.toEntity(requestDTO);
        professorService.create(professor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + professor.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getProfessor(@PathVariable String id) {
        ProfessorResponseDTO responseDTO = professorConverter.toDTO(professorService.getProfessor(UUID.fromString(id)));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid ProfessorRequestDTO requestDTO) {
        professorService.update(UUID.fromString(id), professorConverter.toEntity(requestDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        professorService.delete(professorService.getProfessor(UUID.fromString(id)));
        return ResponseEntity.noContent().build();
    }
}
