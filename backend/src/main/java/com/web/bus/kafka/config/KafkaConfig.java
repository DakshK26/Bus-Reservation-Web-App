package com.web.bus.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String TOPIC_BOOKING_CREATED = "booking-created";
    public static final String TOPIC_BOOKING_CONFIRMED = "booking-confirmed";
    public static final String TOPIC_BOOKING_CANCELLED = "booking-cancelled";

    public static final String GROUP_CONFIRMATION = "booking-confirmation";
    public static final String GROUP_ANALYTICS = "analytics-pipeline";

    /**
     * Auto-create topics in dev mode only.
     * In production, topics are pre-created on the Aiven Kafka cluster.
     */
    @Bean
    @Profile("dev")
    public NewTopic bookingCreatedTopic() {
        return TopicBuilder.name(TOPIC_BOOKING_CREATED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    @Profile("dev")
    public NewTopic bookingConfirmedTopic() {
        return TopicBuilder.name(TOPIC_BOOKING_CONFIRMED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    @Profile("dev")
    public NewTopic bookingCancelledTopic() {
        return TopicBuilder.name(TOPIC_BOOKING_CANCELLED)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
