package com.sgallalucas.gym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "workout")
public class Workout {
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
