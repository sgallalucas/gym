package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.controllers.DTOs.ProfessorRequestDTO;
import com.sgallalucas.gym.controllers.DTOs.ProfessorResponseDTO;
import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.controllers.mappers.ProfessorMapper;
import com.sgallalucas.gym.services.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ProfessorMapper professorMapper;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ProfessorRequestDTO requestDTO) {
        Professor professor = professorMapper.toEntity(requestDTO);
        professorService.create(professor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + professor.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getProfessor(@PathVariable String id) {
        ProfessorResponseDTO responseDTO = professorMapper.toDTO(professorService.getProfessor(UUID.fromString(id)));
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid ProfessorRequestDTO requestDTO) {
        professorService.update(UUID.fromString(id), professorMapper.toEntity(requestDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        professorService.delete(professorService.getProfessor(UUID.fromString(id)));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ProfessorResponseDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                              @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfessorResponseDTO> responseDTO = professorService.findAll(pageable).map(professorMapper::toDTO);
        return ResponseEntity.ok().body(responseDTO);
    }
}
