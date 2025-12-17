package com.sgallalucas.gym.validators;

import com.sgallalucas.gym.exceptions.DuplicateRecordException;
import com.sgallalucas.gym.model.User;
import com.sgallalucas.gym.repositories.UserRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validation(String login) {
        if (check(login)) {
            throw new DuplicateRecordException("User with login already exists");
        }
    }

    private boolean check(String login) {
        boolean flag;
        User found = userRepository.findByLogin(login);
        return flag = (found == null) ? false : true;
    }
}
