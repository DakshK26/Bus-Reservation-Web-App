package com.web.bus.dto;

import com.web.bus.entity.Bus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BusResponse(
        Long id,
        String busNumber,
        Integer capacity,
        Integer availableSeats,
        LocalDateTime departureTime,
        Long routeId,
        String origin,
        String destination,
        BigDecimal pricePerSeat,
        String companyName
) {
    public static BusResponse from(Bus bus) {
        return new BusResponse(
                bus.getId(),
                bus.getBusNumber(),
                bus.getCapacity(),
                bus.getAvailableSeats(),
                bus.getDepartureTime(),
                bus.getRoute().getId(),
                bus.getRoute().getOrigin(),
                bus.getRoute().getDestination(),
                bus.getRoute().getBasePrice(),
                bus.getRoute().getCompany().getName()
        );
    }
}
