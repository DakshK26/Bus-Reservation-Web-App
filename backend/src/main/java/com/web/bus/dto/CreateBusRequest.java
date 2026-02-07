package com.web.bus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record CreateBusRequest(
        @NotNull(message = "Route ID is required")
        Long routeId,

        @NotBlank(message = "Bus number is required")
        String busNumber,

        @NotNull(message = "Capacity is required")
        @Positive(message = "Capacity must be positive")
        Integer capacity,

        @NotNull(message = "Departure time is required")
        LocalDateTime departureTime
) {}
