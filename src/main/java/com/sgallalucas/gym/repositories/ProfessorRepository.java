package com.sgallalucas.gym.repositories;

import com.sgallalucas.gym.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<Professor, UUID> {

    Professor findByEmail(String email);
}
