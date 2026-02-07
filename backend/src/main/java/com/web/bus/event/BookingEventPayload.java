package com.web.bus.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Versioned Kafka event schema for booking lifecycle events.
 * Serialized as JSON and published to booking-created, booking-confirmed, booking-cancelled topics.
 */
public record BookingEventPayload(
        UUID eventId,
        int schemaVersion,
        String eventType,
        Instant timestamp,
        BookingData payload
) {
    public record BookingData(
            Long bookingId,
            Long customerId,
            Long busId,
            Long routeId,
            Long companyId,
            String origin,
            String destination,
            int seatsBooked,
            BigDecimal totalPrice,
            String status
    ) {}

    public static BookingEventPayload create(UUID eventId, String eventType, BookingData data) {
        return new BookingEventPayload(eventId, 1, eventType, Instant.now(), data);
    }
}
