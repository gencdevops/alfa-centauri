package com.example.cgrestaurant.configuration;


import com.example.cgrestaurant.feign.FeignErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = {"com.example.cgrestaurant.feign", "com.example.cgrestaurant.service"})
@Configuration
public class FeignClientConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;


    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.BASIC;
        }
    }


