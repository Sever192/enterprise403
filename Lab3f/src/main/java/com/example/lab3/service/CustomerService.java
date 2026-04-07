package com.example.lab3.service;

import com.example.lab3.dto.CustomerCreateDto;
import com.example.lab3.dto.CustomerDto;
import com.example.lab3.entity.Customer;
import com.example.lab3.exception.CustomerNotFoundException;
import com.example.lab3.jms.NotificationProducer;
import com.example.lab3.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final NotificationProducer notificationProducer;

    @Transactional
    public CustomerDto createCustomer(CustomerCreateDto dto) {
        log.info("Создание нового клиента: {}", dto.getEmail());

        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer = customerRepository.save(customer);

        notificationProducer.sendWelcomeEmail(
                customer.getId(),
                customer.getEmail(),
                customer.getFirstName()
        );

        log.info("Клиент создан с ID: {}", customer.getId());
        return convertToDto(customer);
    }

    public CustomerDto getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private CustomerDto convertToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setCreatedAt(customer.getCreatedAt());
        return dto;
    }
}