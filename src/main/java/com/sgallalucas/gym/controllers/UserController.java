package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.controllers.DTOs.UserRegisterDTO;
import com.sgallalucas.gym.controllers.mappers.UserMapper;
import com.sgallalucas.gym.model.User;
import com.sgallalucas.gym.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterDTO registerDTO) {
        User user = userMapper.toEntity(registerDTO);
        userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + user.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }
}
