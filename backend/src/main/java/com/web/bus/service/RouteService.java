package com.web.bus.service;

import com.web.bus.entity.Company;
import com.web.bus.entity.Route;
import com.web.bus.exception.ResourceNotFoundException;
import com.web.bus.repository.RouteRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Cacheable(value = "routeSearch", key = "#origin + '-' + #destination")
    public List<Route> searchRoutes(String origin, String destination) {
        return routeRepository.searchByOriginAndDestination(origin, destination);
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route", "id", id));
    }

    public List<Route> getRoutesByCompany(Long companyId) {
        return routeRepository.findByCompanyId(companyId);
    }

    public List<String> getDistinctOrigins() {
        return routeRepository.findDistinctOrigins();
    }

    public List<String> getDistinctDestinations() {
        return routeRepository.findDistinctDestinations();
    }

    @Transactional
    public Route createRoute(Company company, String origin, String destination,
                             Double distanceKm, Integer durationMinutes, BigDecimal basePrice) {
        Route route = new Route(company, origin, destination, distanceKm, durationMinutes, basePrice);
        return routeRepository.save(route);
    }
}
