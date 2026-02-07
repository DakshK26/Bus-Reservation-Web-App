package com.web.bus.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BookingRequest(
        @NotNull(message = "Bus ID is required")
        Long busId,

        @NotNull(message = "Number of seats is required")
        @Min(value = 1, message = "Must book at least 1 seat")
        Integer seats
) {}
