package com.caaasperr.Alcoholic._common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Alcoholic")
                .version("0.0.1")
                .description("Alcoholic API Document");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
