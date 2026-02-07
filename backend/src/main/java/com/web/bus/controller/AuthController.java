package com.web.bus.controller;

import com.web.bus.dto.*;
import com.web.bus.entity.Customer;
import com.web.bus.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Customer Authentication", description = "Customer registration and login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new customer account")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        Customer customer = authService.registerCustomer(
                request.username(), request.name(), request.email(), request.password());
        String token = authService.loginCustomer(request.username(), request.password());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, "CUSTOMER", customer.getId(), customer.getName()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with customer credentials")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        String token = authService.loginCustomer(request.username(), request.password());
        return ResponseEntity.ok(new AuthResponse(token, "CUSTOMER", null, request.username()));
    }
}
