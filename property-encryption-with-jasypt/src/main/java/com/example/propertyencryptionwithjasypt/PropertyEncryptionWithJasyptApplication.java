package com.example.propertyencryptionwithjasypt;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEncryptableProperties
public class PropertyEncryptionWithJasyptApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyEncryptionWithJasyptApplication.class, args);
    }

    @Bean
    public CommandLineRunner getEncryptedProperties(ConfigurableApplicationContext applicationContext){
        var logger = LoggerFactory.getLogger(PropertyEncryptionWithJasyptApplication.class);
        return (args -> {
            System.out.println("\n");
            logger.info("Decrypted datasource username = " + applicationContext.getEnvironment().getProperty("spring.datasource.username"));
            logger.info("Decrypted datasource password = " + applicationContext.getEnvironment().getProperty("spring.datasource.password"));
            logger.info("Decrypted custom property value = " + applicationContext.getEnvironment().getProperty("encrypted.value"));
        });
    }

}
