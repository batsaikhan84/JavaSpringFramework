package com.example.demo.controller;

import com.example.demo.Repository.CustomerRepository;
import com.example.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    @Value("$sprint.application.name")
    String appName;
    @Autowired
    private CustomerRepository customerRepository;
    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }
    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable long id) throws Exception {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new Exception("ID: " + id + " not found" ));
        return ResponseEntity.ok().body(customer);
    }
    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
    @DeleteMapping("/customers/{id}")
    public Map<String, String> deleteCustomer(@PathVariable(value = "id") long id) throws Exception {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new Exception("ID: " + id + " not found" ));
        customerRepository.delete(customer);
        Map<String, String> deleteCustomer = new HashMap<>();
        deleteCustomer.put("Person is deleted", "by id: " + id);
        return deleteCustomer;
    }
    @PutMapping("customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") long id, @RequestBody Customer customerDetails) throws Exception {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new Exception("ID: " + id + " not found" ));
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        final Customer updateCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(updateCustomer);
    }
}
