package com.web.bus.dto;

import com.web.bus.entity.Route;

import java.math.BigDecimal;

public record RouteResponse(
        Long id,
        String origin,
        String destination,
        Double distanceKm,
        Integer durationMinutes,
        BigDecimal basePrice,
        String companyName,
        Long companyId
) {
    public static RouteResponse from(Route route) {
        return new RouteResponse(
                route.getId(),
                route.getOrigin(),
                route.getDestination(),
                route.getDistanceKm(),
                route.getDurationMinutes(),
                route.getBasePrice(),
                route.getCompany().getName(),
                route.getCompany().getId()
        );
    }
}
