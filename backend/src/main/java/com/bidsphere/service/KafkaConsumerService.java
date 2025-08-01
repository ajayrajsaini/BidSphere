package com.bidsphere.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "auction-events", groupId = "auction-consumer-group")
    public void listen(String message) {
        System.out.println("Received Kafka message: " + message);
        // You can trigger cache updates, notifications, etc.
    }
}
