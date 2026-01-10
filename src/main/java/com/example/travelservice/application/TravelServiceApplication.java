package com.example.travelservice.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.travelservice")
@MapperScan("com.example.travelservice.mapper")
public class TravelServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TravelServiceApplication.class, args);
    }
}

