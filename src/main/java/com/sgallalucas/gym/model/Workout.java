package com.sgallalucas.gym.model;

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
    private String type;
    private String description;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Workout() {}

    public Workout(UUID id, String name, String type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    // Acessa indiretamente o professor
    public Professor getProfessor() {
        return student.getProfessor();
    }
}
