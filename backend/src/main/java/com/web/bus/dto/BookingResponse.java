package com.web.bus.dto;

import com.web.bus.entity.Booking;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        Integer seatsBooked,
        String status,
        BigDecimal totalPrice,
        Instant createdAt,
        Instant confirmedAt,
        Long busId,
        String busNumber,
        LocalDateTime departureTime,
        String origin,
        String destination,
        String companyName,
        String customerName
) {
    public static BookingResponse from(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getSeatsBooked(),
                booking.getStatus().name(),
                booking.getTotalPrice(),
                booking.getCreatedAt(),
                booking.getConfirmedAt(),
                booking.getBus().getId(),
                booking.getBus().getBusNumber(),
                booking.getBus().getDepartureTime(),
                booking.getBus().getRoute().getOrigin(),
                booking.getBus().getRoute().getDestination(),
                booking.getBus().getRoute().getCompany().getName(),
                booking.getCustomer().getName()
        );
    }
}
