package com.example.patientservice.kafka;

import com.example.patientservice.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.event.PatientEvent;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();
        log.info("Sending event to Kafka topic: {}", patient.getName());

        try {
            kafkaTemplate.send("patient", event.toByteArray());
        } catch (Exception e) {
            log.error("Error while sending event to Kafka topic: {}", event);
            log.error("Error: {}", e.getMessage());
        }
    }
}
