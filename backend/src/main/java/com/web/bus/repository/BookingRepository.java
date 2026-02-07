package com.web.bus.repository;

import com.web.bus.entity.Booking;
import com.web.bus.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b JOIN FETCH b.bus bus JOIN FETCH bus.route r " +
           "JOIN FETCH r.company WHERE b.customer.id = :customerId ORDER BY b.createdAt DESC")
    List<Booking> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT b FROM Booking b JOIN FETCH b.bus bus JOIN FETCH bus.route r " +
           "JOIN FETCH r.company JOIN FETCH b.customer WHERE b.id = :id")
    Optional<Booking> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT b FROM Booking b WHERE b.bus.route.company.id = :companyId ORDER BY b.createdAt DESC")
    List<Booking> findByCompanyId(@Param("companyId") Long companyId);

    long countByStatus(BookingStatus status);
}
