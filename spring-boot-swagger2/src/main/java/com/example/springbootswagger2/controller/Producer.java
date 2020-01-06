package com.example.springbootswagger2.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final String TOPIC = "topictest";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    



    public void sendMessage(String message, String topic) {
        this.kafkaTemplate.send(topic, message);
    }
}