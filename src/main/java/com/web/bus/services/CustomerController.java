package com.web.bus.services;

import com.web.bus.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    /*
    Get All Customers
     */
    @GetMapping
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /*
    Get Customer By Id
     */
    @GetMapping("/{id}")

    /**
     Method to create a new customer
     @param customer the customer to be created
     */
    @PostMapping
    public void createCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
    }

    /**
     Method to update an existing customer
     @param username the username of the customer to be updated
     @param customer the new customer information
     @return a response entity indicating success or failure of update
     */
    @PutMapping("/{username}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String username, @RequestBody Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findByUsername(username);
        if (!existingCustomer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        customer.setUsername(username);
        customerRepository.replaceByUsername(customer, username);
        return ResponseEntity.ok().build();
    }

    /**
     Method to delete an existing customer
     @param username the username of the customer to be deleted
     @return a response entity indicating success or failure of delete
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String username) {
        if (!customerRepository.existsByUsername(username)) {
            return ResponseEntity.notFound().build();
        }
        customerRepository.deleteByUsername(username);
        return ResponseEntity.ok().build();
    }

    /**
     Method to find an existing customer by username
     @param username the username of the customer to be found
     @return a response entity containing the found customer or indicating failure
     */
    @GetMapping("/{username}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (!customer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer.get());
    }

    /**

     Method to check if a given username is already taken by another customer.
     @param username the username to check for
     @return true if the username is already taken, false otherwise
     */
    public boolean isUsernameTaken(String username) {
        return customerRepository.findByUsername(username).isPresent();
    }
}

