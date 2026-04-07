package com.example.lab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class Lab3Application {
    public static void main(String[] args) {
        SpringApplication.run(Lab3Application.class, args);
        System.out.println("=== Асинхронное приложение запущено ===");
        System.out.println("=== H2 Console: http://localhost:8080/h2-console ===");
    }
}