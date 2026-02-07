package com.web.bus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bus.entity.*;
import com.web.bus.exception.BadRequestException;
import com.web.bus.repository.BookingEventRepository;
import com.web.bus.repository.BookingRepository;
import com.web.bus.repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock private BookingRepository bookingRepository;
    @Mock private BookingEventRepository bookingEventRepository;
    @Mock private BusRepository busRepository;
    @Spy private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks private BookingService bookingService;

    private Customer customer;
    private Company company;
    private Route route;
    private Bus bus;

    @BeforeEach
    void setUp() {
        company = new Company("TestBus", "test@bus.com", "hash");
        company.setId(1L);

        customer = new Customer("alice", "Alice", "alice@test.com", "hash");
        customer.setId(1L);

        route = new Route(company, "New York", "Boston", 346.0, 240, new BigDecimal("29.99"));
        route.setId(1L);

        bus = new Bus(route, "NY-BO-1", 40, LocalDateTime.now().plusDays(1));
        bus.setId(1L);
        bus.setAvailableSeats(40);
    }

    @Test
    void createBooking_success() {
        when(busRepository.save(any(Bus.class))).thenReturn(bus);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> {
            Booking b = inv.getArgument(0);
            b.setId(1L);
            return b;
        });
        when(bookingEventRepository.save(any(BookingEvent.class))).thenAnswer(inv -> inv.getArgument(0));

        Booking result = bookingService.createBooking(customer, bus, 2);

        assertThat(result).isNotNull();
        assertThat(result.getSeatsBooked()).isEqualTo(2);
        assertThat(result.getStatus()).isEqualTo(BookingStatus.PENDING);
        assertThat(result.getTotalPrice()).isEqualByComparingTo(new BigDecimal("59.98"));
        assertThat(bus.getAvailableSeats()).isEqualTo(38);

        verify(bookingEventRepository).save(any(BookingEvent.class));
    }

    @Test
    void createBooking_notEnoughSeats_throwsBadRequest() {
        bus.setAvailableSeats(1);

        assertThatThrownBy(() -> bookingService.createBooking(customer, bus, 5))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Not enough seats");
    }

    @Test
    void createBooking_zeroSeats_throwsBadRequest() {
        assertThatThrownBy(() -> bookingService.createBooking(customer, bus, 0))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("positive");
    }

    @Test
    void cancelBooking_success() {
        Booking booking = new Booking(customer, bus, 2, new BigDecimal("59.98"));
        booking.setId(1L);
        booking.setStatus(BookingStatus.CONFIRMED);

        when(bookingRepository.findByIdWithDetails(1L)).thenReturn(Optional.of(booking));
        when(busRepository.save(any(Bus.class))).thenReturn(bus);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> inv.getArgument(0));
        when(bookingEventRepository.save(any(BookingEvent.class))).thenAnswer(inv -> inv.getArgument(0));

        Booking result = bookingService.cancelBooking(1L, 1L);

        assertThat(result.getStatus()).isEqualTo(BookingStatus.CANCELLED);
        assertThat(bus.getAvailableSeats()).isEqualTo(42);
    }

    @Test
    void cancelBooking_wrongCustomer_throwsBadRequest() {
        Booking booking = new Booking(customer, bus, 2, new BigDecimal("59.98"));
        booking.setId(1L);

        when(bookingRepository.findByIdWithDetails(1L)).thenReturn(Optional.of(booking));

        assertThatThrownBy(() -> bookingService.cancelBooking(1L, 999L))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("own bookings");
    }

    @Test
    void confirmBooking_success() {
        Booking booking = new Booking(customer, bus, 2, new BigDecimal("59.98"));
        booking.setId(1L);

        when(bookingRepository.findByIdWithDetails(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> inv.getArgument(0));
        when(bookingEventRepository.save(any(BookingEvent.class))).thenAnswer(inv -> inv.getArgument(0));

        Booking result = bookingService.confirmBooking(1L);

        assertThat(result.getStatus()).isEqualTo(BookingStatus.CONFIRMED);
        assertThat(result.getConfirmedAt()).isNotNull();
    }
}
