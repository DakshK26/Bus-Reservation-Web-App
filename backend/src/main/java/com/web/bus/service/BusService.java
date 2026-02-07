package com.web.bus.service;

import com.web.bus.entity.Bus;
import com.web.bus.entity.Route;
import com.web.bus.exception.ResourceNotFoundException;
import com.web.bus.repository.BusRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BusService {

    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Cacheable(value = "busAvailability", key = "#routeId")
    public List<Bus> getAvailableBuses(Long routeId) {
        return busRepository.findAvailableByRouteId(routeId, LocalDateTime.now());
    }

    public Bus getBusById(Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "id", id));
    }

    public List<Bus> getBusesByCompany(Long companyId) {
        return busRepository.findByCompanyId(companyId);
    }

    @Transactional
    public Bus createBus(Route route, String busNumber, Integer capacity, LocalDateTime departureTime) {
        Bus bus = new Bus(route, busNumber, capacity, departureTime);
        return busRepository.save(bus);
    }
}
