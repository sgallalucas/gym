package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.controllers.DTOs.StudentRequestDTO;
import com.sgallalucas.gym.controllers.DTOs.StudentResponseDTO;
import com.sgallalucas.gym.controllers.mappers.StudentMapper;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.services.StudentService;
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
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid StudentRequestDTO requestDTO) {
        Student student = studentMapper.toEntity(requestDTO);
        studentService.create(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + student.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable String id) {
        Student student = studentService.getStudent(UUID.fromString(id));
        StudentResponseDTO responseDTO = studentMapper.toDTO(student);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid StudentRequestDTO requestDTO) {
        studentService.update(UUID.fromString(id), studentMapper.toEntity(requestDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        studentService.delete(studentService.getStudent(UUID.fromString(id)));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<StudentResponseDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentResponseDTO> responseDTO = studentService.findAll(pageable).map(studentMapper::toDTO);
        return ResponseEntity.ok().body(responseDTO);
    }
}
