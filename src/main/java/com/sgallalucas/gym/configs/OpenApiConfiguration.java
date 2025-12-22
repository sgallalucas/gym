package com.sgallalucas.gym.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "gym-api",
                version = "v1",
                contact = @Contact(
                        name = "Lucas Sgalla",
                        email = "lucas.sgalla02@gmail.com"
                )
        )
)
public class OpenApiConfiguration {
}
