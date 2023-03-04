package com.example.cgrestaurant.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("cg-restaurant") String description,
                                 @Value("v1.0") String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("cg-restaurant service")
                        .version(version)
                        .description(description)
                        .license(new License().name("restaurant service")));
    }

}