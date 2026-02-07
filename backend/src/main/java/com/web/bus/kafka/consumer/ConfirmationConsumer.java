package com.web.bus.kafka.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bus.kafka.config.KafkaConfig;
import com.web.bus.repository.BookingEventRepository;
import com.web.bus.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Consumes booking-created events and confirms bookings after simulated payment processing.
 * Idempotent: checks eventId before processing to handle redeliveries.
 */
@Component
public class ConfirmationConsumer {

    private static final Logger log = LoggerFactory.getLogger(ConfirmationConsumer.class);

    private final BookingService bookingService;
    private final BookingEventRepository bookingEventRepository;
    private final ObjectMapper objectMapper;

    public ConfirmationConsumer(BookingService bookingService,
                                BookingEventRepository bookingEventRepository,
                                ObjectMapper objectMapper) {
        this.bookingService = bookingService;
        this.bookingEventRepository = bookingEventRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = KafkaConfig.TOPIC_BOOKING_CREATED,
            groupId = KafkaConfig.GROUP_CONFIRMATION
    )
    public void onBookingCreated(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);
            UUID eventId = UUID.fromString(root.get("eventId").asText());
            Long bookingId = root.get("payload").get("bookingId").asLong();

            log.info("Received booking-created event: eventId={}, bookingId={}", eventId, bookingId);

            // Simulate payment processing delay
            Thread.sleep(2000);

            // Confirm the booking
            bookingService.confirmBooking(bookingId);

            log.info("Booking confirmed: bookingId={}", bookingId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Confirmation interrupted", e);
        } catch (Exception e) {
            log.error("Error processing booking-created event", e);
        }
    }
}
