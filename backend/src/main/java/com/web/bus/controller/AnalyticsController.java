package com.web.bus.controller;

import com.web.bus.analytics.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/analytics")
@Tag(name = "Analytics", description = "Read-only analytics endpoints powered by BigQuery + dbt")
@SecurityRequirement(name = "bearerAuth")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/bookings-by-day")
    @Operation(summary = "Get booking counts and revenue aggregated by day")
    public ResponseEntity<List<Map<String, Object>>> getBookingsByDay() {
        return ResponseEntity.ok(analyticsService.getBookingsByDay());
    }

    @GetMapping("/revenue-by-route")
    @Operation(summary = "Get revenue and booking counts aggregated by route")
    public ResponseEntity<List<Map<String, Object>>> getRevenueByRoute() {
        return ResponseEntity.ok(analyticsService.getRevenueByRoute());
    }

    @GetMapping("/revenue-by-company")
    @Operation(summary = "Get revenue and booking counts aggregated by company")
    public ResponseEntity<List<Map<String, Object>>> getRevenueByCompany() {
        return ResponseEntity.ok(analyticsService.getRevenueByCompany());
    }

    @GetMapping("/confirmation-latency")
    @Operation(summary = "Get booking confirmation latency percentiles (p50, p95, p99)")
    public ResponseEntity<Map<String, Object>> getConfirmationLatency() {
        return ResponseEntity.ok(analyticsService.getConfirmationLatency());
    }
}
