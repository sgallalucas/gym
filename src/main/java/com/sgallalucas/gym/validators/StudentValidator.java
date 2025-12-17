package com.sgallalucas.gym.validators;

import com.sgallalucas.gym.exceptions.DuplicateRecordException;
import com.sgallalucas.gym.model.Student;
import com.sgallalucas.gym.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentValidator {

    private final StudentRepository studentRepository;

    public void validation(String email) {
        if (check(email)) {
            throw new DuplicateRecordException(String.format("student with %s email already exists", email));
        }
    }

    private boolean check(String email) {
        boolean flag;
        Student found = studentRepository.findByEmail(email);
        return flag = (found == null) ? false : true;
    }
}
