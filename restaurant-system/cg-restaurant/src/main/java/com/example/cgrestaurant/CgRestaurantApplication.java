package com.example.cgrestaurant;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "DC Payment Service", version = "1.0"))
public class CgRestaurantApplication {

    public static void main(String[] args) {
        SpringApplication.run(CgRestaurantApplication.class, args);
    }

}
