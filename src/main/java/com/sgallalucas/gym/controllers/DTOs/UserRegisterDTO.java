package com.sgallalucas.gym.controllers.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(
        @NotBlank(message = "required field")
        String login,
        @NotBlank(message = "required field")
        String password,
        @NotBlank(message = "required field")
        String roles
) {
}
