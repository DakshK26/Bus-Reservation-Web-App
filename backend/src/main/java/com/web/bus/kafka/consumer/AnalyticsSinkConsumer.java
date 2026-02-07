package com.web.bus.kafka.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bus.analytics.BigQuerySinkService;
import com.web.bus.kafka.config.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Consumes all booking lifecycle events and sinks them to BigQuery.
 * Idempotent: uses eventId as BigQuery row deduplication key.
 */
@Component
public class AnalyticsSinkConsumer {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsSinkConsumer.class);

    private final BigQuerySinkService bigQuerySinkService;
    private final ObjectMapper objectMapper;

    public AnalyticsSinkConsumer(BigQuerySinkService bigQuerySinkService,
                                 ObjectMapper objectMapper) {
        this.bigQuerySinkService = bigQuerySinkService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = {
                    KafkaConfig.TOPIC_BOOKING_CREATED,
                    KafkaConfig.TOPIC_BOOKING_CONFIRMED,
                    KafkaConfig.TOPIC_BOOKING_CANCELLED
            },
            groupId = KafkaConfig.GROUP_ANALYTICS
    )
    public void onBookingEvent(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);
            UUID eventId = UUID.fromString(root.get("eventId").asText());
            String eventType = root.get("eventType").asText();

            log.debug("Analytics sink received event: type={}, eventId={}", eventType, eventId);

            bigQuerySinkService.insertEvent(message);
        } catch (Exception e) {
            log.error("Error sinking event to BigQuery", e);
        }
    }
}
