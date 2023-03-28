package com.example.acserviceone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AcServiceOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcServiceOneApplication.class, args);
    }

}
