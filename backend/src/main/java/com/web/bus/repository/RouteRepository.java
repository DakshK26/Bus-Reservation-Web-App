package com.web.bus.repository;

import com.web.bus.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("SELECT r FROM Route r JOIN FETCH r.company WHERE " +
           "LOWER(r.origin) LIKE LOWER(CONCAT('%', :origin, '%')) AND " +
           "LOWER(r.destination) LIKE LOWER(CONCAT('%', :destination, '%'))")
    List<Route> searchByOriginAndDestination(@Param("origin") String origin,
                                             @Param("destination") String destination);

    @Query("SELECT r FROM Route r JOIN FETCH r.company WHERE r.company.id = :companyId")
    List<Route> findByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT DISTINCT r.origin FROM Route r ORDER BY r.origin")
    List<String> findDistinctOrigins();

    @Query("SELECT DISTINCT r.destination FROM Route r ORDER BY r.destination")
    List<String> findDistinctDestinations();
}
