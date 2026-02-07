package com.web.bus.analytics;

import com.web.bus.entity.Booking;
import com.web.bus.entity.BookingStatus;
import com.web.bus.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Serves pre-aggregated analytics data.
 * In production, queries BigQuery dbt mart tables.
 * In dev mode, computes analytics from PostgreSQL/H2 directly.
 */
@Service
@Transactional(readOnly = true)
public class AnalyticsService {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsService.class);

    private final boolean bigqueryEnabled;
    private final BookingRepository bookingRepository;

    public AnalyticsService(@Value("${app.bigquery.enabled:false}") boolean bigqueryEnabled,
                            BookingRepository bookingRepository) {
        this.bigqueryEnabled = bigqueryEnabled;
        this.bookingRepository = bookingRepository;
    }

    @Cacheable(value = "analytics", key = "'bookings-by-day'")
    public List<Map<String, Object>> getBookingsByDay() {
        if (bigqueryEnabled) {
            return queryBigQuery("SELECT * FROM `bus_reservation.bookings_by_day` ORDER BY booking_date DESC LIMIT 30");
        }
        return computeBookingsByDay();
    }

    @Cacheable(value = "analytics", key = "'revenue-by-route'")
    public List<Map<String, Object>> getRevenueByRoute() {
        if (bigqueryEnabled) {
            return queryBigQuery("SELECT * FROM `bus_reservation.revenue_by_route` ORDER BY total_revenue DESC LIMIT 20");
        }
        return computeRevenueByRoute();
    }

    @Cacheable(value = "analytics", key = "'revenue-by-company'")
    public List<Map<String, Object>> getRevenueByCompany() {
        if (bigqueryEnabled) {
            return queryBigQuery("SELECT * FROM `bus_reservation.revenue_by_company` ORDER BY total_revenue DESC");
        }
        return computeRevenueByCompany();
    }

    @Cacheable(value = "analytics", key = "'confirmation-latency'")
    public Map<String, Object> getConfirmationLatency() {
        if (bigqueryEnabled) {
            var results = queryBigQuery("SELECT * FROM `bus_reservation.booking_confirmation_latency` LIMIT 1");
            return results.isEmpty() ? Map.of() : results.get(0);
        }
        return computeConfirmationLatency();
    }

    // --- Fallback analytics computed from OLTP database (dev mode) ---

    private List<Map<String, Object>> computeBookingsByDay() {
        List<Booking> allBookings = bookingRepository.findAll();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

        Map<String, List<Booking>> byDay = allBookings.stream()
                .collect(Collectors.groupingBy(b -> fmt.format(b.getCreatedAt())));

        return byDay.entrySet().stream()
                .sorted(Map.Entry.<String, List<Booking>>comparingByKey().reversed())
                .map(entry -> {
                    List<Booking> bookings = entry.getValue();
                    BigDecimal totalRevenue = bookings.stream()
                            .map(Booking::getTotalPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("booking_date", entry.getKey());
                    row.put("total_bookings", bookings.size());
                    row.put("total_revenue", totalRevenue);
                    row.put("avg_seats", bookings.stream().mapToInt(Booking::getSeatsBooked).average().orElse(0));
                    return row;
                })
                .toList();
    }

    private List<Map<String, Object>> computeRevenueByRoute() {
        List<Booking> allBookings = bookingRepository.findAll();

        Map<String, List<Booking>> byRoute = allBookings.stream()
                .collect(Collectors.groupingBy(b ->
                        b.getBus().getRoute().getOrigin() + " -> " + b.getBus().getRoute().getDestination()));

        return byRoute.entrySet().stream()
                .map(entry -> {
                    List<Booking> bookings = entry.getValue();
                    BigDecimal totalRevenue = bookings.stream()
                            .map(Booking::getTotalPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("route", entry.getKey());
                    row.put("total_bookings", bookings.size());
                    row.put("total_revenue", totalRevenue);
                    row.put("avg_price", totalRevenue.divide(BigDecimal.valueOf(bookings.size()), 2, RoundingMode.HALF_UP));
                    return row;
                })
                .sorted((a, b) -> ((BigDecimal) b.get("total_revenue")).compareTo((BigDecimal) a.get("total_revenue")))
                .toList();
    }

    private List<Map<String, Object>> computeRevenueByCompany() {
        List<Booking> allBookings = bookingRepository.findAll();

        Map<String, List<Booking>> byCompany = allBookings.stream()
                .collect(Collectors.groupingBy(b -> b.getBus().getRoute().getCompany().getName()));

        return byCompany.entrySet().stream()
                .map(entry -> {
                    List<Booking> bookings = entry.getValue();
                    BigDecimal totalRevenue = bookings.stream()
                            .map(Booking::getTotalPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("company", entry.getKey());
                    row.put("total_bookings", bookings.size());
                    row.put("total_revenue", totalRevenue);
                    return row;
                })
                .sorted((a, b) -> ((BigDecimal) b.get("total_revenue")).compareTo((BigDecimal) a.get("total_revenue")))
                .toList();
    }

    private Map<String, Object> computeConfirmationLatency() {
        List<Booking> confirmed = bookingRepository.findAll().stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED && b.getConfirmedAt() != null)
                .toList();

        if (confirmed.isEmpty()) {
            return Map.of("p50_ms", 0, "p95_ms", 0, "p99_ms", 0, "avg_ms", 0, "sample_size", 0);
        }

        List<Long> latencies = confirmed.stream()
                .map(b -> b.getConfirmedAt().toEpochMilli() - b.getCreatedAt().toEpochMilli())
                .sorted()
                .toList();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("p50_ms", percentile(latencies, 50));
        result.put("p95_ms", percentile(latencies, 95));
        result.put("p99_ms", percentile(latencies, 99));
        result.put("avg_ms", latencies.stream().mapToLong(Long::longValue).average().orElse(0));
        result.put("sample_size", latencies.size());
        return result;
    }

    private long percentile(List<Long> sorted, int p) {
        int idx = (int) Math.ceil(p / 100.0 * sorted.size()) - 1;
        return sorted.get(Math.max(0, idx));
    }

    private List<Map<String, Object>> queryBigQuery(String sql) {
        // Production: Use BigQuery client to execute SQL
        // BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
        // QueryJobConfiguration config = QueryJobConfiguration.of(sql);
        // TableResult result = bigquery.query(config);
        log.info("BigQuery query: {}", sql);
        return List.of();
    }
}
