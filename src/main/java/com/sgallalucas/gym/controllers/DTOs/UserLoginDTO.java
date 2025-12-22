package com.sgallalucas.gym.controllers.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "User (Login)")
public record UserLoginDTO(
        @NotBlank
        String login,
        @NotBlank
        String password
) {
}
