package com.sgallalucas.gym.services;

import com.sgallalucas.gym.model.User;
import com.sgallalucas.gym.repositories.UserRepository;
import com.sgallalucas.gym.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator validator;

    public void save(User user) {
        validator.validation(user.getLogin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
