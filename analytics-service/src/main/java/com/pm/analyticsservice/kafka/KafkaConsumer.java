package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.event.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        // Deserialize the event
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            // perform any necessary processing with the event
            // analyze the event

            log.info("Received Patient Event: [PatientId={}, Name={}, Email={}, EventType={}]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail(),
                    patientEvent.getEventType());
        } catch (InvalidProtocolBufferException e) {
            log.error("event={}", e);
            log.error("Error while deserializing event: {}", e.getMessage());
        }
        // Process the event

        System.out.println("Received event: " + new String(event));
    }
}
