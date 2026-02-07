package com.web.bus.controller;

import com.web.bus.dto.*;
import com.web.bus.entity.Company;
import com.web.bus.entity.Route;
import com.web.bus.security.AuthenticatedUser;
import com.web.bus.service.BusService;
import com.web.bus.service.CompanyService;
import com.web.bus.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@Tag(name = "Company Management", description = "Manage routes and buses for a company")
@SecurityRequirement(name = "bearerAuth")
public class CompanyController {

    private final CompanyService companyService;
    private final RouteService routeService;
    private final BusService busService;

    public CompanyController(CompanyService companyService,
                             RouteService routeService,
                             BusService busService) {
        this.companyService = companyService;
        this.routeService = routeService;
        this.busService = busService;
    }

    @GetMapping("/routes")
    @Operation(summary = "Get all routes for the authenticated company")
    public ResponseEntity<List<RouteResponse>> getMyRoutes(
            @AuthenticationPrincipal AuthenticatedUser user) {
        var routes = routeService.getRoutesByCompany(user.userId());
        return ResponseEntity.ok(routes.stream().map(RouteResponse::from).toList());
    }

    @PostMapping("/routes")
    @Operation(summary = "Create a new route")
    public ResponseEntity<RouteResponse> createRoute(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody CreateRouteRequest request) {
        Company company = companyService.getCompanyById(user.userId());
        Route route = routeService.createRoute(company, request.origin(), request.destination(),
                request.distanceKm(), request.durationMinutes(), request.basePrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(RouteResponse.from(route));
    }

    @PostMapping("/buses")
    @Operation(summary = "Add a bus schedule to a route")
    public ResponseEntity<BusResponse> createBus(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody CreateBusRequest request) {
        Route route = routeService.getRouteById(request.routeId());
        var bus = busService.createBus(route, request.busNumber(),
                request.capacity(), request.departureTime());
        return ResponseEntity.status(HttpStatus.CREATED).body(BusResponse.from(bus));
    }

    @GetMapping("/buses")
    @Operation(summary = "Get all buses for the authenticated company")
    public ResponseEntity<List<BusResponse>> getMyBuses(
            @AuthenticationPrincipal AuthenticatedUser user) {
        var buses = busService.getBusesByCompany(user.userId());
        return ResponseEntity.ok(buses.stream().map(BusResponse::from).toList());
    }
}
