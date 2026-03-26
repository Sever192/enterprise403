package project.service;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import project.enity.Customer;
import project.repository.CustomerRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    public final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> createCustomers(List<Customer> customers) {
       for (Customer customer : customers){
           if(customer.getCreatedAt() == null){
               customer.setCreatedAt(LocalDateTime.now());
           }
       }
        return customerRepository.saveAll(customers);
    }

    public Customer readCustomer(Long id){
        return customerRepository.findById(id).orElseThrow();
    }

    public List<Customer> readCustomerAll(){
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Long id, Customer customer){
        Customer updateCustomer = customerRepository.findById(id).orElseThrow();
        updateCustomer.setFirstName(customer.getFirstName());
        updateCustomer.setLastName(customer.getLastName());
        updateCustomer.setEmail(customer.getEmail());
        return customerRepository.save(updateCustomer);
    }

    public void deleteCustomer(Long id){
        Customer deleteCustomer = customerRepository.findById(id).orElseThrow();
        customerRepository.delete(deleteCustomer);
    }
}
