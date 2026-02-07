package com.web.bus.seed;

import com.web.bus.entity.*;
import com.web.bus.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Seeds the database with realistic demo data on startup.
 * Only runs when app.seed.enabled=true and the database is empty.
 */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(CompanyRepository companyRepository,
                      CustomerRepository customerRepository,
                      RouteRepository routeRepository,
                      BusRepository busRepository,
                      BookingRepository bookingRepository,
                      PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.routeRepository = routeRepository;
        this.busRepository = busRepository;
        this.bookingRepository = bookingRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (companyRepository.count() > 0) {
            log.info("Database already seeded, skipping.");
            return;
        }
        log.info("Seeding database with demo data...");

        // --- Companies ---
        String hash = passwordEncoder.encode("password123");
        Company greyhound = companyRepository.save(new Company("Greyhound", "info@greyhound.com", hash));
        Company flixbus = companyRepository.save(new Company("FlixBus", "info@flixbus.com", hash));
        Company megabus = companyRepository.save(new Company("MegaBus", "info@megabus.com", hash));
        Company redCoach = companyRepository.save(new Company("RedCoach", "info@redcoach.com", hash));
        Company ourBus = companyRepository.save(new Company("OurBus", "info@ourbus.com", hash));
        log.info("Seeded 5 companies");

        // --- Customers ---
        String custHash = passwordEncoder.encode("password123");
        Customer alice = customerRepository.save(new Customer("alice", "Alice Johnson", "alice@example.com", custHash));
        Customer bob = customerRepository.save(new Customer("bob", "Bob Smith", "bob@example.com", custHash));
        Customer charlie = customerRepository.save(new Customer("charlie", "Charlie Brown", "charlie@example.com", custHash));
        Customer diana = customerRepository.save(new Customer("diana", "Diana Prince", "diana@example.com", custHash));
        Customer ethan = customerRepository.save(new Customer("ethan", "Ethan Hunt", "ethan@example.com", custHash));
        customerRepository.save(new Customer("fiona", "Fiona Apple", "fiona@example.com", custHash));
        customerRepository.save(new Customer("george", "George Lucas", "george@example.com", custHash));
        customerRepository.save(new Customer("hannah", "Hannah Montana", "hannah@example.com", custHash));
        customerRepository.save(new Customer("ivan", "Ivan Drago", "ivan@example.com", custHash));
        customerRepository.save(new Customer("julia", "Julia Roberts", "julia@example.com", custHash));
        log.info("Seeded 10 customers");

        // --- Routes (with hardcoded realistic distances/durations) ---
        List<Route> routes = List.of(
            routeRepository.save(new Route(greyhound, "New York", "Boston", 346.0, 240, new BigDecimal("29.99"))),
            routeRepository.save(new Route(greyhound, "New York", "Philadelphia", 151.0, 120, new BigDecimal("19.99"))),
            routeRepository.save(new Route(greyhound, "New York", "Washington DC", 365.0, 270, new BigDecimal("34.99"))),
            routeRepository.save(new Route(flixbus, "New York", "Boston", 346.0, 250, new BigDecimal("24.99"))),
            routeRepository.save(new Route(flixbus, "Los Angeles", "San Francisco", 616.0, 360, new BigDecimal("39.99"))),
            routeRepository.save(new Route(flixbus, "Los Angeles", "Las Vegas", 435.0, 270, new BigDecimal("29.99"))),
            routeRepository.save(new Route(flixbus, "Chicago", "Detroit", 450.0, 300, new BigDecimal("27.99"))),
            routeRepository.save(new Route(megabus, "Chicago", "Milwaukee", 148.0, 105, new BigDecimal("14.99"))),
            routeRepository.save(new Route(megabus, "Chicago", "Indianapolis", 290.0, 195, new BigDecimal("22.99"))),
            routeRepository.save(new Route(megabus, "Boston", "New York", 346.0, 245, new BigDecimal("19.99"))),
            routeRepository.save(new Route(megabus, "Washington DC", "New York", 365.0, 275, new BigDecimal("24.99"))),
            routeRepository.save(new Route(redCoach, "Miami", "Orlando", 380.0, 240, new BigDecimal("34.99"))),
            routeRepository.save(new Route(redCoach, "Miami", "Tampa", 449.0, 270, new BigDecimal("39.99"))),
            routeRepository.save(new Route(redCoach, "Orlando", "Jacksonville", 225.0, 150, new BigDecimal("22.99"))),
            routeRepository.save(new Route(ourBus, "New York", "Albany", 248.0, 165, new BigDecimal("24.99"))),
            routeRepository.save(new Route(ourBus, "Boston", "Providence", 80.0, 60, new BigDecimal("12.99"))),
            routeRepository.save(new Route(ourBus, "San Francisco", "Sacramento", 140.0, 105, new BigDecimal("16.99"))),
            routeRepository.save(new Route(ourBus, "Seattle", "Portland", 279.0, 195, new BigDecimal("24.99"))),
            routeRepository.save(new Route(greyhound, "Dallas", "Houston", 385.0, 240, new BigDecimal("29.99"))),
            routeRepository.save(new Route(greyhound, "Atlanta", "Charlotte", 395.0, 255, new BigDecimal("32.99"))),
            routeRepository.save(new Route(flixbus, "Denver", "Salt Lake City", 819.0, 480, new BigDecimal("49.99"))),
            routeRepository.save(new Route(megabus, "Philadelphia", "Pittsburgh", 490.0, 345, new BigDecimal("29.99")))
        );
        log.info("Seeded {} routes", routes.size());

        // --- Buses (multiple departures per route) ---
        LocalDateTime baseDate = LocalDateTime.now().plusDays(1).withHour(6).withMinute(0).withSecond(0).withNano(0);
        int busCount = 0;

        for (Route route : routes) {
            // Create 2-3 bus departures per route over the next few days
            for (int dayOffset = 0; dayOffset < 3; dayOffset++) {
                LocalDateTime morning = baseDate.plusDays(dayOffset).withHour(7 + (int)(Math.random() * 3));
                LocalDateTime afternoon = baseDate.plusDays(dayOffset).withHour(13 + (int)(Math.random() * 3));

                String prefix = route.getOrigin().substring(0, 2).toUpperCase() +
                        route.getDestination().substring(0, 2).toUpperCase();

                busRepository.save(new Bus(route, prefix + "-" + (busCount + 1), 40 + (int)(Math.random() * 16), morning));
                busCount++;
                busRepository.save(new Bus(route, prefix + "-" + (busCount + 1), 40 + (int)(Math.random() * 16), afternoon));
                busCount++;
            }
        }
        log.info("Seeded {} buses", busCount);

        // --- Sample Bookings ---
        List<Bus> allBuses = busRepository.findAll();
        if (!allBuses.isEmpty()) {
            createSampleBooking(alice, allBuses.get(0), 2);
            createSampleBooking(bob, allBuses.get(1), 1);
            createSampleBooking(charlie, allBuses.get(2), 3);
            createSampleBooking(diana, allBuses.get(3), 1);
            createSampleBooking(ethan, allBuses.get(4), 2);
            createSampleBooking(alice, allBuses.get(5), 1);
            createSampleBooking(bob, allBuses.get(6), 2);
            createSampleBooking(charlie, allBuses.get(8), 1);
        }
        log.info("Seeded sample bookings");

        log.info("Database seeding complete!");
    }

    private void createSampleBooking(Customer customer, Bus bus, int seats) {
        if (bus.getAvailableSeats() >= seats) {
            BigDecimal totalPrice = bus.getRoute().getBasePrice().multiply(BigDecimal.valueOf(seats));
            Booking booking = new Booking(customer, bus, seats, totalPrice);
            booking.setStatus(BookingStatus.CONFIRMED);
            bus.setAvailableSeats(bus.getAvailableSeats() - seats);
            busRepository.save(bus);
            bookingRepository.save(booking);
        }
    }
}
