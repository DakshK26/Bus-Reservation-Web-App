package com.web.bus.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Transactional outbox table for Kafka event publishing.
 * Events are written in the same DB transaction as the booking mutation,
 * then asynchronously published to Kafka by the outbox producer.
 */
@Entity
@Table(name = "booking_events", indexes = {
        @Index(name = "idx_event_booking", columnList = "booking_id"),
        @Index(name = "idx_event_published", columnList = "published"),
        @Index(name = "idx_event_id", columnList = "eventId", unique = true)
})
public class BookingEvent extends AbstractEntity {

    @Column(nullable = false, unique = true, updatable = false)
    private UUID eventId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false, length = 30)
    private String eventType;

    @Column(nullable = false)
    private Integer schemaVersion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Boolean published;

    protected BookingEvent() {
    }

    public BookingEvent(Booking booking, String eventType, String payload) {
        this.eventId = UUID.randomUUID();
        this.booking = booking;
        this.eventType = eventType;
        this.schemaVersion = 1;
        this.payload = payload;
        this.published = false;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(Integer schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}
