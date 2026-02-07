package com.web.bus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
@EmbeddedKafka(partitions = 1, topics = {"booking-created", "booking-confirmed", "booking-cancelled"})
class BusApplicationTests {

    @Test
    void contextLoads() {
    }
}
