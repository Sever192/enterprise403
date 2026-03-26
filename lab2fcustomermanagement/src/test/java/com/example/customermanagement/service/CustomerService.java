package com.example.customermanagement.service;

import com.example.customermanagement.entity.Customer;
import com.example.customermanagement.repository.CustomerRepository;
import com.example.customermanagement.specification.CustomerSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Cacheable(value = "allCustomers", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Customer> getAllCustomersWithFilters(String firstName, String lastName,
                                                     String email, Pageable pageable) {
        Specification<Customer> spec = Specification
                .where(CustomerSpecifications.hasFirstName(firstName))
                .and(CustomerSpecifications.hasLastName(lastName))
                .and(CustomerSpecifications.hasEmail(email));

        return customerRepository.findAll(spec, pageable);
    }

    @Cacheable(value = "customers", key = "#id")
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @CacheEvict(value = {"customers", "allCustomers"}, allEntries = true)
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @CacheEvict(value = {"customers", "allCustomers"}, allEntries = true)
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        customer.setAddress(customerDetails.getAddress());
        return customerRepository.save(customer);
    }

    @CacheEvict(value = {"customers", "allCustomers"}, allEntries = true)
    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }
}