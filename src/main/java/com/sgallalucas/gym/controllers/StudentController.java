package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.model.DTOs.StudentRequestDTO;
import com.sgallalucas.gym.model.DTOs.StudentResponseDTO;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.services.StudentService;
import com.sgallalucas.gym.utils.converters.StudentConverter;
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
    private final StudentConverter studentConverter;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid StudentRequestDTO requestDTO) {
        Student student = studentConverter.toEntity(requestDTO);
        studentService.create(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + student.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable String id) {
        Student student = studentService.getStudent(UUID.fromString(id));
        StudentResponseDTO responseDTO = studentConverter.toDTO(student);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid StudentRequestDTO requestDTO) {
        studentService.update(UUID.fromString(id), studentConverter.toEntity(requestDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        studentService.delete(studentService.getStudent(UUID.fromString(id)));
        return ResponseEntity.noContent().build();
    }
}
