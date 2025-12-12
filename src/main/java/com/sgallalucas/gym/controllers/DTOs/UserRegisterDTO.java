package com.sgallalucas.gym.controllers.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(
        @NotBlank
        String login,
        @NotBlank
        String password,
        @NotBlank
        String role
) {
}
