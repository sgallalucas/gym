package com.sgallalucas.gym.repositories;

import com.sgallalucas.gym.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {
}
