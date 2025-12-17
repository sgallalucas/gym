package com.sgallalucas.gym.controllers.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank
        String login,
        @NotBlank
        String password
) {
}
