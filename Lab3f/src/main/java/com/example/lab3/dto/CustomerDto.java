package com.example.lab3.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
}