package project.conroller;


import org.springframework.web.bind.annotation.*;
import project.enity.Customer;
import project.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/batch")
    public List<Customer> createCustomers(@RequestBody List<Customer> customers) {
        return customerService.createCustomers(customers);
    }
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        return customerService.updateCustomer(id, customer);
    }
    @GetMapping("/find={id}")
    public Customer readCustomer(@PathVariable Long id){
        return customerService.readCustomer(id);
    }

    @GetMapping("/all")
    public List<Customer> readCustomerAll(){
        return customerService.readCustomerAll();
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }
}
