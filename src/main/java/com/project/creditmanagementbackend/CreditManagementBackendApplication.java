package com.project.creditmanagementbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "com.project.creditmanagementbackend.domain.entity")
public class CreditManagementBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreditManagementBackendApplication.class, args);
    }
}