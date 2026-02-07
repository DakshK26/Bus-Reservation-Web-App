package com.web.bus.repository;

import com.web.bus.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    @Query("SELECT b FROM Bus b JOIN FETCH b.route r JOIN FETCH r.company " +
           "WHERE b.route.id = :routeId AND b.availableSeats > 0 " +
           "AND b.departureTime > :now ORDER BY b.departureTime")
    List<Bus> findAvailableByRouteId(@Param("routeId") Long routeId,
                                     @Param("now") LocalDateTime now);

    @Query("SELECT b FROM Bus b JOIN FETCH b.route r JOIN FETCH r.company " +
           "WHERE r.company.id = :companyId ORDER BY b.departureTime")
    List<Bus> findByCompanyId(@Param("companyId") Long companyId);
}
