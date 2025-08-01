package com.bidsphere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC = "auction-events";

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    public void sendAuctionEvent(String message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Sent Kafka message: " + message);
    }
}
