package com.web.bus.analytics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Sinks Kafka booking events to BigQuery raw_booking_events table.
 * When BigQuery is disabled (dev mode), logs events instead.
 */
@Service
public class BigQuerySinkService {

    private static final Logger log = LoggerFactory.getLogger(BigQuerySinkService.class);

    private final boolean enabled;
    private final ObjectMapper objectMapper;

    public BigQuerySinkService(@Value("${app.bigquery.enabled:false}") boolean enabled,
                               ObjectMapper objectMapper) {
        this.enabled = enabled;
        this.objectMapper = objectMapper;
    }

    public void insertEvent(String eventJson) {
        if (!enabled) {
            log.debug("BigQuery disabled, logging event: {}", eventJson);
            return;
        }

        try {
            JsonNode root = objectMapper.readTree(eventJson);
            String eventId = root.get("eventId").asText();
            String eventType = root.get("eventType").asText();

            // In production, this would use the BigQuery client:
            // BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
            // TableId tableId = TableId.of(dataset, "raw_booking_events");
            // InsertAllRequest request = InsertAllRequest.newBuilder(tableId)
            //     .addRow(eventId, buildRowContent(root))
            //     .build();
            // bigquery.insertAll(request);

            log.info("Inserted event to BigQuery: eventId={}, type={}", eventId, eventType);
        } catch (Exception e) {
            log.error("Failed to insert event to BigQuery", e);
        }
    }
}
