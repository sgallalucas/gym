package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.controllers.DTOs.StudentRequestDTO;
import com.sgallalucas.gym.controllers.DTOs.StudentResponseDTO;
import com.sgallalucas.gym.controllers.mappers.StudentMapper;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.services.StudentService;
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
@RequestMapping("/students")
@RequiredArgsConstructor
@Tag(name = "Students")
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create", description = "Register a new Student by passing fields name, email, birth date, genre and professor id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student registered successfully."),
            @ApiResponse(responseCode = "422", description = "Student with invalid data."),
            @ApiResponse(responseCode = "409", description = "Student already exists."),
    })
    public ResponseEntity<Void> create(@RequestBody @Valid StudentRequestDTO requestDTO) {
        Student student = studentMapper.toEntity(requestDTO);
        studentService.create(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + student.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @Operation(summary = "Get", description = "Find a Student by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student successfully found."),
            @ApiResponse(responseCode = "404", description = "Student not found."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable String id) {
        Student student = studentService.getStudent(UUID.fromString(id));
        StudentResponseDTO responseDTO = studentMapper.toDTO(student);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update", description = "Updates a Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student successfully updated."),
            @ApiResponse(responseCode = "404", description = "Student not found."),
            @ApiResponse(responseCode = "422", description = "Invalid data for Student update."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid StudentRequestDTO requestDTO) {
        studentService.update(UUID.fromString(id), studentMapper.toEntity(requestDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete", description = "Delete a Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student successfully deleted."),
            @ApiResponse(responseCode = "404", description = "Student not found."),
            @ApiResponse(responseCode = "200", description = "Not allowed operation. Students who have workout(s) cannot be deleted."),
            @ApiResponse(responseCode = "400", description = "Invalid id provided."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        studentService.delete(studentService.getStudent(UUID.fromString(id)));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get", description = "Find all students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list with all students or a empty list."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })

    public ResponseEntity<Page<StudentResponseDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentResponseDTO> responseDTO = studentService.findAll(pageable).map(studentMapper::toDTO);
        return ResponseEntity.ok().body(responseDTO);
    }
}
