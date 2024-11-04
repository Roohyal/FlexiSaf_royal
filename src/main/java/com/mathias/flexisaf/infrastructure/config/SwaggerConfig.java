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
    public OpenAPI createOpenAPIConfig(){
        //This creates an OpenAPI object
        return new OpenAPI()
                //This sets general information about the API
                .info(new Info().title("Task it ")
                        .version("1.0")
                        .description("A Task management Api"))
                //This adds a security schemes component
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                        )
                )
                //Add security requirement to use JWT bearer token for all endpoints
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }


}