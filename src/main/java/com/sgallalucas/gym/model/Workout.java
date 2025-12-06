package com.sgallalucas.gym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sgallalucas.gym.model.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    @JsonIgnore
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    public Workout() {}

    public Workout(UUID id, String name, String description, Type type, Professor professor, Student student) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.professor = professor;
        this.student = student;
    }
}
