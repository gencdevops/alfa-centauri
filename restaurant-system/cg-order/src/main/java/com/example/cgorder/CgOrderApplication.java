package com.example.cgorder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class CgOrderApplication {


    public static void main(String[] args) {
        SpringApplication.run(CgOrderApplication.class, args);
    }
}
