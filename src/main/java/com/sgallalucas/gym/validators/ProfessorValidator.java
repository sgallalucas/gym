package com.sgallalucas.gym.validators;

import com.sgallalucas.gym.exceptions.DuplicateRecordException;
import com.sgallalucas.gym.model.Professor;
import com.sgallalucas.gym.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfessorValidator {

    private final ProfessorRepository professorRepository;

    public void validation(String email) {
        if (check(email)) {
            throw new DuplicateRecordException("This email already exists");
        }
    }

    private boolean check(String email) {
        boolean flag;
        Professor found = professorRepository.findByEmail(email);
        return flag = (found == null) ? false : true;
    }
}
