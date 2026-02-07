package com.web.bus.service;

import com.web.bus.entity.Company;
import com.web.bus.entity.Customer;
import com.web.bus.exception.ConflictException;
import com.web.bus.repository.CompanyRepository;
import com.web.bus.repository.CustomerRepository;
import com.web.bus.security.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerRepository customerRepository,
                       CompanyRepository companyRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public Customer registerCustomer(String username, String name, String email, String rawPassword) {
        if (customerRepository.existsByUsername(username)) {
            throw new ConflictException("Username already taken: " + username);
        }
        if (customerRepository.existsByEmail(email)) {
            throw new ConflictException("Email already registered: " + email);
        }

        String hash = passwordEncoder.encode(rawPassword);
        Customer customer = new Customer(username, name, email, hash);
        return customerRepository.save(customer);
    }

    public String loginCustomer(String username, String rawPassword) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(rawPassword, customer.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return jwtTokenProvider.generateToken(customer.getId(), customer.getUsername(), "CUSTOMER");
    }

    @Transactional
    public Company registerCompany(String name, String email, String rawPassword) {
        if (companyRepository.existsByName(name)) {
            throw new ConflictException("Company name already taken: " + name);
        }
        if (companyRepository.existsByEmail(email)) {
            throw new ConflictException("Email already registered: " + email);
        }

        String hash = passwordEncoder.encode(rawPassword);
        Company company = new Company(name, email, hash);
        return companyRepository.save(company);
    }

    public String loginCompany(String email, String rawPassword) {
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(rawPassword, company.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return jwtTokenProvider.generateToken(company.getId(), company.getEmail(), "COMPANY");
    }
}
