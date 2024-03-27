package com.example.KeyboardArenaProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KeyboardArenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeyboardArenaApplication.class, args);
    }

}