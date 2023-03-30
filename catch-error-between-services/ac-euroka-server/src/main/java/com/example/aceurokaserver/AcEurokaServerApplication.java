package com.example.aceurokaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AcEurokaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcEurokaServerApplication.class, args);
    }

}
