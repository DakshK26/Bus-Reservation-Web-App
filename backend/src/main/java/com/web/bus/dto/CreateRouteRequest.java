package com.web.bus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateRouteRequest(
        @NotBlank(message = "Origin is required")
        String origin,

        @NotBlank(message = "Destination is required")
        String destination,

        @NotNull(message = "Distance is required")
        @Positive(message = "Distance must be positive")
        Double distanceKm,

        @NotNull(message = "Duration is required")
        @Positive(message = "Duration must be positive")
        Integer durationMinutes,

        @NotNull(message = "Base price is required")
        @Positive(message = "Price must be positive")
        BigDecimal basePrice
) {}
