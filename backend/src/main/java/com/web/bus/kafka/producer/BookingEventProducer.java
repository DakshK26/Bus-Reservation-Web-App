package com.web.bus.kafka.producer;

import com.web.bus.entity.BookingEvent;
import com.web.bus.kafka.config.KafkaConfig;
import com.web.bus.repository.BookingEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Transactional outbox producer: polls unpublished BookingEvents from the database
 * and publishes them to the appropriate Kafka topic.
 * Guarantees at-least-once delivery even if Kafka is temporarily unavailable.
 */
@Component
public class BookingEventProducer {

    private static final Logger log = LoggerFactory.getLogger(BookingEventProducer.class);

    private final BookingEventRepository bookingEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public BookingEventProducer(BookingEventRepository bookingEventRepository,
                                KafkaTemplate<String, String> kafkaTemplate) {
        this.bookingEventRepository = bookingEventRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void publishPendingEvents() {
        List<BookingEvent> unpublished = bookingEventRepository.findByPublishedFalseOrderByCreatedAtAsc();

        for (BookingEvent event : unpublished) {
            String topic = mapEventTypeToTopic(event.getEventType());
            String key = event.getBooking().getId().toString();

            try {
                kafkaTemplate.send(topic, key, event.getPayload()).get();
                event.setPublished(true);
                bookingEventRepository.save(event);
                log.debug("Published event {} to topic {}", event.getEventId(), topic);
            } catch (Exception e) {
                log.error("Failed to publish event {} to Kafka, will retry", event.getEventId(), e);
                break; // Stop processing to maintain order
            }
        }
    }

    private String mapEventTypeToTopic(String eventType) {
        return switch (eventType) {
            case "BOOKING_CREATED" -> KafkaConfig.TOPIC_BOOKING_CREATED;
            case "BOOKING_CONFIRMED" -> KafkaConfig.TOPIC_BOOKING_CONFIRMED;
            case "BOOKING_CANCELLED" -> KafkaConfig.TOPIC_BOOKING_CANCELLED;
            default -> throw new IllegalArgumentException("Unknown event type: " + eventType);
        };
    }
}
