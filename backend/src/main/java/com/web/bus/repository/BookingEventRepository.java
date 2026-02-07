package com.web.bus.repository;

import com.web.bus.entity.BookingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingEventRepository extends JpaRepository<BookingEvent, Long> {

    List<BookingEvent> findByPublishedFalseOrderByCreatedAtAsc();

    boolean existsByEventId(UUID eventId);
}
