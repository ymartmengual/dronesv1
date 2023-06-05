package com.example.drones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesApplication {
    //localhost:8080/h2-console/

    public static void main(String[] args) {
        SpringApplication.run(DronesApplication.class, args);
    }

}
