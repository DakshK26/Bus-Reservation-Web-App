package com.web.bus.controller;

import com.web.bus.dto.BookingRequest;
import com.web.bus.dto.BookingResponse;
import com.web.bus.entity.Bus;
import com.web.bus.entity.Customer;
import com.web.bus.exception.ResourceNotFoundException;
import com.web.bus.repository.CustomerRepository;
import com.web.bus.security.AuthenticatedUser;
import com.web.bus.service.BookingService;
import com.web.bus.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings", description = "Create, view, and cancel bookings")
@SecurityRequirement(name = "bearerAuth")
public class BookingController {

    private final BookingService bookingService;
    private final BusService busService;
    private final CustomerRepository customerRepository;

    public BookingController(BookingService bookingService,
                             BusService busService,
                             CustomerRepository customerRepository) {
        this.bookingService = bookingService;
        this.busService = busService;
        this.customerRepository = customerRepository;
    }

    @PostMapping
    @Operation(summary = "Create a new booking")
    public ResponseEntity<BookingResponse> createBooking(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody BookingRequest request) {
        Customer customer = customerRepository.findById(user.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", user.userId()));
        Bus bus = busService.getBusById(request.busId());
        var booking = bookingService.createBooking(customer, bus, request.seats());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BookingResponse.from(booking));
    }

    @GetMapping
    @Operation(summary = "Get all bookings for the authenticated customer")
    public ResponseEntity<List<BookingResponse>> getMyBookings(
            @AuthenticationPrincipal AuthenticatedUser user) {
        var bookings = bookingService.getCustomerBookings(user.userId());
        return ResponseEntity.ok(bookings.stream().map(BookingResponse::from).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking details by ID")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(BookingResponse.from(bookingService.getBookingById(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel a booking")
    public ResponseEntity<BookingResponse> cancelBooking(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable Long id) {
        var booking = bookingService.cancelBooking(id, user.userId());
        return ResponseEntity.ok(BookingResponse.from(booking));
    }
}
