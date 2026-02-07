package com.web.bus.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bus.entity.*;
import com.web.bus.event.BookingEventPayload;
import com.web.bus.exception.BadRequestException;
import com.web.bus.exception.ResourceNotFoundException;
import com.web.bus.repository.BookingEventRepository;
import com.web.bus.repository.BookingRepository;
import com.web.bus.repository.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final BookingEventRepository bookingEventRepository;
    private final BusRepository busRepository;
    private final ObjectMapper objectMapper;

    public BookingService(BookingRepository bookingRepository,
                          BookingEventRepository bookingEventRepository,
                          BusRepository busRepository,
                          ObjectMapper objectMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingEventRepository = bookingEventRepository;
        this.busRepository = busRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Creates a booking with optimistic locking on seat availability.
     * Writes both the Booking and a BookingEvent (outbox) in the same transaction.
     */
    @Transactional
    @CacheEvict(value = "busAvailability", key = "#bus.route.id")
    public Booking createBooking(Customer customer, Bus bus, int seatsRequested) {
        if (seatsRequested <= 0) {
            throw new BadRequestException("Seats requested must be positive");
        }
        if (bus.getAvailableSeats() < seatsRequested) {
            throw new BadRequestException(
                    String.format("Not enough seats available. Requested: %d, Available: %d",
                            seatsRequested, bus.getAvailableSeats()));
        }

        // Decrement seats (optimistic locking via @Version will catch concurrent updates)
        bus.setAvailableSeats(bus.getAvailableSeats() - seatsRequested);
        busRepository.save(bus);

        BigDecimal totalPrice = bus.getRoute().getBasePrice()
                .multiply(BigDecimal.valueOf(seatsRequested));

        Booking booking = new Booking(customer, bus, seatsRequested, totalPrice);
        booking = bookingRepository.save(booking);

        // Write event to outbox in same transaction
        writeOutboxEvent(booking, "BOOKING_CREATED");

        log.info("Booking created: id={}, customer={}, bus={}, seats={}",
                booking.getId(), customer.getUsername(), bus.getBusNumber(), seatsRequested);

        return booking;
    }

    /**
     * Cancels a booking and releases the seats back.
     */
    @Transactional
    @CacheEvict(value = "busAvailability", allEntries = true)
    public Booking cancelBooking(Long bookingId, Long customerId) {
        Booking booking = bookingRepository.findByIdWithDetails(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

        if (!booking.getCustomer().getId().equals(customerId)) {
            throw new BadRequestException("You can only cancel your own bookings");
        }
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        // Return seats to pool
        Bus bus = booking.getBus();
        bus.setAvailableSeats(bus.getAvailableSeats() + booking.getSeatsBooked());
        busRepository.save(bus);

        booking = bookingRepository.save(booking);

        writeOutboxEvent(booking, "BOOKING_CANCELLED");

        log.info("Booking cancelled: id={}", bookingId);
        return booking;
    }

    /**
     * Confirms a booking (called by the Kafka confirmation worker).
     */
    @Transactional
    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findByIdWithDetails(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

        if (booking.getStatus() != BookingStatus.PENDING) {
            log.warn("Booking {} is not PENDING, skipping confirmation. Status: {}",
                    bookingId, booking.getStatus());
            return booking;
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setConfirmedAt(Instant.now());
        booking = bookingRepository.save(booking);

        writeOutboxEvent(booking, "BOOKING_CONFIRMED");

        log.info("Booking confirmed: id={}", bookingId);
        return booking;
    }

    public List<Booking> getCustomerBookings(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
    }

    public List<Booking> getCompanyBookings(Long companyId) {
        return bookingRepository.findByCompanyId(companyId);
    }

    private void writeOutboxEvent(Booking booking, String eventType) {
        Bus bus = booking.getBus();
        Route route = bus.getRoute();

        BookingEventPayload.BookingData data = new BookingEventPayload.BookingData(
                booking.getId(),
                booking.getCustomer().getId(),
                bus.getId(),
                route.getId(),
                route.getCompany().getId(),
                route.getOrigin(),
                route.getDestination(),
                booking.getSeatsBooked(),
                booking.getTotalPrice(),
                booking.getStatus().name()
        );

        BookingEventPayload payload = BookingEventPayload.create(
                java.util.UUID.randomUUID(), eventType, data);

        try {
            String json = objectMapper.writeValueAsString(payload);
            BookingEvent event = new BookingEvent(booking, eventType, json);
            event.setEventId(payload.eventId());
            bookingEventRepository.save(event);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize booking event", e);
            throw new RuntimeException("Failed to serialize booking event", e);
        }
    }
}
