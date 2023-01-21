package com.web.bus.security;
import com.web.bus.entities.Customer;
import com.web.bus.services.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**

 @author: Daksh & Ashwin
 Date: Jan. 2023
 Description: Service class that provides authentication for customers by implementing
              Spring Security's UserDetailsService interface
 */
@Service
public class CustomerAuthenticationService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *

     @param username the username of the customer
     @param password the password of the customer
     @return returns a boolean indicating whether the user has been successfully authenticated.
     if the authentication fails
     */
    public boolean authenticate(String username, String password) {
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);
        if (optionalCustomer.isEmpty()) {
            return false;
        }

        Customer customer = optionalCustomer.get();
        return passwordEncoder.matches(password, customer.getPassword()); // Check if password matches password inside customer
    }

    /**
     Method that loads the customer by their username and returns the customer's
     UserDetails object for authentication.
     @param username the username of the customer
     @return the UserDetails object of the customer
     @throws UsernameNotFoundException if the customer is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (!customer.isPresent()) {
            throw new UsernameNotFoundException("Customer not found with username: " + username);
        }
        return new CustomerPrincipal(customer.get());
    }
}
