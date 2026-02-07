package com.web.bus.controller;

import com.web.bus.dto.BusResponse;
import com.web.bus.dto.RouteResponse;
import com.web.bus.service.BusService;
import com.web.bus.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@Tag(name = "Routes", description = "Search and view bus routes")
public class RouteController {

    private final RouteService routeService;
    private final BusService busService;

    public RouteController(RouteService routeService, BusService busService) {
        this.routeService = routeService;
        this.busService = busService;
    }

    @GetMapping
    @Operation(summary = "Search routes by origin and destination")
    public ResponseEntity<List<RouteResponse>> searchRoutes(
            @RequestParam String origin,
            @RequestParam(name = "dest") String destination) {
        var routes = routeService.searchRoutes(origin, destination);
        return ResponseEntity.ok(routes.stream().map(RouteResponse::from).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get route details by ID")
    public ResponseEntity<RouteResponse> getRoute(@PathVariable Long id) {
        return ResponseEntity.ok(RouteResponse.from(routeService.getRouteById(id)));
    }

    @GetMapping("/{routeId}/buses")
    @Operation(summary = "Get available buses for a route")
    public ResponseEntity<List<BusResponse>> getAvailableBuses(@PathVariable Long routeId) {
        var buses = busService.getAvailableBuses(routeId);
        return ResponseEntity.ok(buses.stream().map(BusResponse::from).toList());
    }

    @GetMapping("/origins")
    @Operation(summary = "Get all distinct origin cities")
    public ResponseEntity<List<String>> getOrigins() {
        return ResponseEntity.ok(routeService.getDistinctOrigins());
    }

    @GetMapping("/destinations")
    @Operation(summary = "Get all distinct destination cities")
    public ResponseEntity<List<String>> getDestinations() {
        return ResponseEntity.ok(routeService.getDistinctDestinations());
    }
}
