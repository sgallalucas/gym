package com.sgallalucas.gym.controllers;

import com.sgallalucas.gym.controllers.DTOs.UserLoginDTO;
import com.sgallalucas.gym.controllers.DTOs.UserRegisterDTO;
import com.sgallalucas.gym.controllers.mappers.UserMapper;
import com.sgallalucas.gym.model.User;
import com.sgallalucas.gym.security.JwtTokenService;
import com.sgallalucas.gym.security.UserDetailsImpl;
import com.sgallalucas.gym.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService  jwtTokenService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Register", description = "Register a new user by passing login, password and role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered."),
            @ApiResponse(responseCode = "422", description = "User with invalid data."),
            @ApiResponse(responseCode = "409", description = "User already exists."),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterDTO registerDTO) {
        User user = userMapper.toEntity(registerDTO);
        userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + user.getId()).buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "User login, generates a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "401", description = "User not authorized."),
    })
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginDTO loginDTO) {
        userService.loginVerification(userMapper.loginDTOtoEntity(loginDTO));
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        String token = jwtTokenService.generateToken((UserDetailsImpl) authentication.getPrincipal());
        return  ResponseEntity.ok().body("token: " + token);
    }
}
