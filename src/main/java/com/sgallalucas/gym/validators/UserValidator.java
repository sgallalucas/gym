package com.sgallalucas.gym.validators;

import com.sgallalucas.gym.exceptions.DuplicateRecordException;
import com.sgallalucas.gym.model.User;
import com.sgallalucas.gym.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validation(String login) {
        if (check(login)) {
            throw new DuplicateRecordException(String.format("User with %s login already exists", login));
        }
    }

    private boolean check(String login) {
        boolean flag;
        User found = userRepository.findByLogin(login);
        return flag = (found == null) ? false : true;
    }
}
