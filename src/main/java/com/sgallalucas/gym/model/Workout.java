package com.sgallalucas.gym.model;

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

    @Enumerated(EnumType.STRING)
    private Type type;
    private String description;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Workout() {}

    public Workout(UUID id, String name, Type type, String description) {
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
