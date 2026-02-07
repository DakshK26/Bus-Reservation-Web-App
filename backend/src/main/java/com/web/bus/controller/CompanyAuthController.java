package com.web.bus.controller;

import com.web.bus.dto.*;
import com.web.bus.entity.Company;
import com.web.bus.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company/auth")
@Tag(name = "Company Authentication", description = "Company registration and login")
public class CompanyAuthController {

    private final AuthService authService;

    public CompanyAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new bus company")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody CompanyRegisterRequest request) {
        Company company = authService.registerCompany(request.name(), request.email(), request.password());
        String token = authService.loginCompany(request.email(), request.password());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, "COMPANY", company.getId(), company.getName()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with company credentials")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody CompanyAuthRequest request) {
        String token = authService.loginCompany(request.email(), request.password());
        return ResponseEntity.ok(new AuthResponse(token, "COMPANY", null, request.email()));
    }
}
