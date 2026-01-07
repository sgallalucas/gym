package com.sgallalucas.gym.services;

import com.sgallalucas.gym.exceptions.DuplicateRecordException;
import com.sgallalucas.gym.exceptions.NotAllowedOperationException;
import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.model.enums.Genre;
import com.sgallalucas.gym.model.enums.Specialty;
import com.sgallalucas.gym.repositories.ProfessorRepository;
import com.sgallalucas.gym.validators.ProfessorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private ProfessorValidator professorValidator;

    Professor professor, professor2, updatedProfessor, invalidProfessor;

    Student student;

    List<Professor> professorsList = new ArrayList<>();

    List<Student> students = new ArrayList<>();

    @BeforeEach
    void setUp() {
        professor = new Professor(UUID.randomUUID(), "Jorge", "jorge@gmail.com", LocalDate.of(2000, 1, 1), Genre.MALE, Specialty.BODYBUILDING);
        professor2 = new Professor(UUID.randomUUID(), "Pedro", "pedro@gmail.com", LocalDate.of(2000, 1, 1), Genre.MALE, Specialty.BODYBUILDING, students);
        updatedProfessor = new Professor(professor.getId(), "Douglas", "douglas@gmail.com", LocalDate.of(2000, 1, 1), Genre.MALE, Specialty.BODYBUILDING);
        invalidProfessor = new Professor(null, null, null, null, null, null);
    }

    @Test
    @DisplayName(value = "create professor with valid data")
    void create() {
        doNothing().when(professorValidator).validation(professor.getEmail());
        when(professorRepository.save(professor)).thenReturn(professor);

        professorService.create(professor);

        verify(professorRepository, times(1)).save(professor);
    }

    @Test
    @DisplayName(value = "create professor with invalid data")
    void createInvalid() {
        doNothing().when(professorValidator).validation(invalidProfessor.getEmail());
        when(professorRepository.save(invalidProfessor)).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> professorService.create(invalidProfessor)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "create professor with duplicate data")
    void createDuplicate() {
        doThrow(DuplicateRecordException.class).when(professorValidator).validation(professor.getEmail());

        assertThatThrownBy(() -> professorService.create(professor)).isInstanceOf(DuplicateRecordException.class);
    }

    @Test
    @DisplayName(value = "find professor by existing id")
    void getProfessor() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(professor));

        Professor sut = professorService.getProfessor(professor.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getId()).isEqualTo(professor.getId());
    }

    @Test
    @DisplayName(value = "find professor by non-existing id")
    void getNonExistingProfessor() {
        when(professorRepository.findById(professor.getId())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> professorService.getProfessor(professor.getId())).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "find professor with invalid id")
    void getInvalidProfessor() {
        when(professorRepository.findById(invalidProfessor.getId())).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> professorService.getProfessor(invalidProfessor.getId())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "update professor with valid data")
    void update() {
        doNothing().when(professorValidator).validation(updatedProfessor.getEmail());
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(professor));

        professorService.update(professor.getId(), updatedProfessor);

        verify(professorRepository, times(1)).save(professor);
        verify(professorValidator, times(1)).validation(updatedProfessor.getEmail());
    }

    @Test
    @DisplayName(value = "update professor with invalid data")
    void updateInvalid() {
        doNothing().when(professorValidator).validation(updatedProfessor.getEmail());
        when(professorRepository.findById(invalidProfessor.getId())).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> professorService.update(invalidProfessor.getId(), updatedProfessor)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "update professor with duplicate data")
    void updateDuplicate() {
        doThrow(DuplicateRecordException.class).when(professorValidator).validation(updatedProfessor.getEmail());

        assertThatThrownBy(() -> professorService.update(professor.getId(), updatedProfessor)).isInstanceOf(DuplicateRecordException.class);
    }

    @Test
    @DisplayName(value = "update non-existing professor")
    void updateNonExistingProfessor() {
        doNothing().when(professorValidator).validation(updatedProfessor.getEmail());
        when(professorRepository.findById(invalidProfessor.getId())).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> professorService.update(invalidProfessor.getId(), updatedProfessor)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "delete existing professor with no student(s)")
    void delete() {
        when(professorRepository.findById(professor2.getId())).thenReturn(Optional.of(professor2));

        professorService.delete(professor2);

        verify(professorRepository, times(1)).delete(professor2);
    }

    @Test
    @DisplayName(value = "delete existing professor with students(s)")
    void deleteNotAllowed() {
        student = new Student(UUID.randomUUID(), "Lucas", "lucas@gmail.com", LocalDate.of(2000, 1, 1), Genre.MALE);
        students.add(student);

        when(professorRepository.findById(professor2.getId())).thenReturn(Optional.of(professor2));

        assertThatThrownBy(() -> professorService.delete(professor2)).isInstanceOf(NotAllowedOperationException.class);
    }

    @Test
    @DisplayName(value = "delete non-existing professor")
    void deleteNonExistingProfessor() {
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professorService.delete(professor)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "delete professor with invalid id")
    void deleteWithInvalidId() {
        when(professorRepository.findById(professor.getId())).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> professorService.delete(professor)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "find all professors")
    void findAll() {
        professorsList.add(professor);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Professor> page = new PageImpl<>(professorsList, pageable, professorsList.size());

        when(professorRepository.findAll(pageable)).thenReturn((page));

        Page<Professor> sut = professorService.findAll(pageable);

        assertThat(sut.getTotalElements()).isEqualTo(1);
        assertThat(sut.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName(value = "find all professors returns empty")
    void findAllEmpty() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Professor> page = new PageImpl<>(professorsList, pageable, professorsList.size());

        when(professorRepository.findAll(pageable)).thenReturn((page));

        Page<Professor> sut = professorService.findAll(pageable);

        assertThat(sut.getTotalElements()).isEqualTo(0);
        assertThat(sut.getTotalPages()).isEqualTo(0);
    }
}
