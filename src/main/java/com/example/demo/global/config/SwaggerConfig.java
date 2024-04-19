package com.example.demo.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {
    @Bean
    // @Profile("!prod") // 운영환경 실행하지 않음 !
    public OpenAPI openAPI() {
        Components components = new Components();
        return new OpenAPI().components(components);
    }
}
