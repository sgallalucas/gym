package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.model.DTOs.StudentRequestDTO;
import com.sgallalucas.gym.model.DTOs.StudentResponseDTO;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid StudentRequestDTO requestDTO) {
        Student student = studentService.toEntity(requestDTO);
        studentService.create(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + student.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable String id) {
        Student student = studentService.getStudent(UUID.fromString(id));
        StudentResponseDTO responseDTO = studentService.toDTO(student);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid StudentRequestDTO requestDTO) {
        studentService.update(UUID.fromString(id), studentService.toEntity(requestDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        studentService.delete(studentService.getStudent(UUID.fromString(id)));
        return ResponseEntity.noContent().build();
    }
}
