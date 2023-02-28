package com.example.cgconfigserver;

import com.example.cgconfigserver.config.ConfigServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class CgConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CgConfigServerApplication.class, args);
    }

}
