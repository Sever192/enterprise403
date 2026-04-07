package com.example.lab3.controller;

import com.example.lab3.dto.CustomerCreateDto;
import com.example.lab3.dto.CustomerDto;
import com.example.lab3.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerCreateDto dto) {
        long start = System.currentTimeMillis();
        CustomerDto created = customerService.createCustomer(dto);
        long elapsed = System.currentTimeMillis() - start;
        log.info("Клиент создан за {} мс (асинхронная отправка email не ждала)", elapsed);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
}