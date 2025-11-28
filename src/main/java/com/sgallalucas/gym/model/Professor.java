package com.sgallalucas.gym.model;

import com.sgallalucas.gym.model.enums.Genre;
import com.sgallalucas.gym.model.enums.Specialty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private Genre genre;
    private Specialty specialty;

    @OneToMany(mappedBy = "professor")
    private List<Student> students;

    public Professor() {}

    public Professor(UUID id, String name, String email, LocalDate birthDate, Genre genre, Specialty specialty) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.genre = genre;
        this.specialty = specialty;
    }
}
