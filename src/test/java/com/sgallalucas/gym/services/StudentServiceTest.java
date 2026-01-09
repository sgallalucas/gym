package com.sgallalucas.gym.services;

import com.sgallalucas.gym.exceptions.DuplicateRecordException;
import com.sgallalucas.gym.exceptions.NotAllowedOperationException;
import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.model.Workout;
import com.sgallalucas.gym.model.enums.Genre;
import com.sgallalucas.gym.repositories.StudentRepository;
import com.sgallalucas.gym.validators.StudentValidator;
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
public class StudentServiceTest {

    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @Mock
    StudentValidator studentValidator;

    Student student, student2, invalidStudent, updatedStudent;

    List<Student> studentList = new ArrayList<>();

    Professor professor;

    List<Workout> workoutList;

    @BeforeEach
    void setUp() {
        student = new Student(UUID.randomUUID(), "Lucas", "lucas@gmail.com", LocalDate.of(2000, 1, 1), Genre.MALE, professor, null);
        student2 = new Student(UUID.randomUUID(), "Amanda", "amanda@gmail.com", LocalDate.of(2000, 1, 1), Genre.FEMALE, professor, workoutList);
        invalidStudent = new Student(null, null, null, null, null, null, null);
        updatedStudent = new Student(student.getId(), "Junior", "junior@gmail.com",  LocalDate.of(2000, 1, 1), Genre.MALE);
    }

    @Test
    @DisplayName(value = "create student with valid data")
    void create() {
        doNothing().when(studentValidator).validation(student.getEmail());
        when(studentRepository.save(student)).thenReturn(student);

        studentService.create(student);

        verify(studentRepository, times(1)).save(student);
    }

    @Test
    @DisplayName(value = "create student with invalid data")
    void createInvalid() {
        doNothing().when(studentValidator).validation(invalidStudent.getEmail());
        when(studentRepository.save(invalidStudent)).thenThrow(IllegalArgumentException.class);

        assertThatThrownBy(() -> studentService.create(invalidStudent)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "create student with duplicate date")
    void createDuplicate() {
        doThrow(DuplicateRecordException.class).when(studentValidator).validation(student.getEmail());

        assertThatThrownBy(() -> studentService.create(student)).isInstanceOf(DuplicateRecordException.class);
    }

    @Test
    @DisplayName(value = "find student by existing id")
    void getStudent() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        Student sut = studentService.getStudent(student.getId());

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(student);
    }

    @Test
    @DisplayName(value = "find student by non-existing id")
    void getNonExistingStudent() {
        when(studentRepository.findById(student.getId())).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> studentService.getStudent(student.getId())).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "find by student by invalid id")
    void getInvalidId() {
        when(studentRepository.findById(invalidStudent.getId())).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> studentService.getStudent(invalidStudent.getId())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "update student with valid data")
    void update() {
        doNothing().when(studentValidator).validation(updatedStudent.getEmail());
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        studentService.update(student.getId(), updatedStudent);

        verify(studentRepository, times(1)).save(student);
        verify(studentValidator, times(1)).validation(updatedStudent.getEmail());
    }

    @Test
    @DisplayName(value = "update student with invalid data")
    void updateInvalid() {
        doNothing().when(studentValidator).validation(invalidStudent.getEmail());
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> studentService.update(student.getId(), invalidStudent)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "update non-existent student")
    void updateNonExistent() {
        doNothing().when(studentValidator).validation(updatedStudent.getEmail());
        when(studentRepository.findById(student.getId())).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> studentService.update(student.getId(), updatedStudent)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "update with invalid id")
    void updateInvalidId() {
        doNothing().when(studentValidator).validation(updatedStudent.getEmail());
        when(studentRepository.findById(invalidStudent.getId())).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> studentService.update(invalidStudent.getId(), updatedStudent)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "update with duplicate data")
    void updateDuplicate() {
        doThrow(DuplicateRecordException.class).when(studentValidator).validation(updatedStudent.getEmail());

        assertThatThrownBy(() -> studentService.update(student.getId(), updatedStudent)).isInstanceOf(DuplicateRecordException.class);
    }

    @Test
    @DisplayName(value = "delete existing student with no workouts")
    void delete() {
      when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

      studentService.delete(student);

      verify(studentRepository, times(1)).delete(student);
    }

    @Test
    @DisplayName(value = "delete existing student with workouts")
    void deleteWithWorkouts() {
        when(studentRepository.findById(student2.getId())).thenReturn(Optional.of(student2));
        doThrow(NotAllowedOperationException.class).when(studentRepository).delete(student2);

        assertThatThrownBy(() -> studentService.delete(student2)).isInstanceOf(NotAllowedOperationException.class);
    }

    @Test
    @DisplayName(value = "delete student with non-existing id")
    void deleteNonExistingId() {
        when(studentRepository.findById(student.getId())).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> studentService.delete(student)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName(value = "delete student with invalid id")
    void deleteWithInvalidId() {
        when(studentRepository.findById(invalidStudent.getId())).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> studentService.delete(invalidStudent)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(value = "find all students")
    void findAll() {
        studentList.add(student);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Student> page = new PageImpl<>(studentList, pageable, studentList.size());

        when(studentRepository.findAll(pageable)).thenReturn(page);

        Page<Student> sut = studentService.findAll(pageable);

        assertThat(sut.getTotalElements()).isEqualTo(1);
        assertThat(sut.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName(value = "find all returns empty")
    void findAllEmpty() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Student> page = new PageImpl<>(studentList, pageable, studentList.size());

        when(studentRepository.findAll(pageable)).thenReturn(page);

        Page<Student> sut = studentService.findAll(pageable);

        assertThat(sut.getTotalElements()).isEqualTo(0);
        assertThat(sut.getTotalPages()).isEqualTo(0);
    }
}

