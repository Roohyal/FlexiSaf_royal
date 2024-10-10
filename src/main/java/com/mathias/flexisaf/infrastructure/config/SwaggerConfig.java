package com.mathias.flexisaf.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI createOpenAPIConfig() {
        return new OpenAPI()
                // General API information
                .info(new Info()
                        .title("Task Management API")
                        .version("1.0")
                        .description("API documentation for a Task management application"));


    }
}