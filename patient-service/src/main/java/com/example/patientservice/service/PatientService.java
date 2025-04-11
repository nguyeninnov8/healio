package com.example.patientservice.service;

import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.exception.EmailAlreadyExistsException;
import com.example.patientservice.exception.PatientNotFoundException;
import com.example.patientservice.grpc.BillingServiceGrpcClient;
import com.example.patientservice.kafka.KafkaProducer;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email).orElse(null);
    }

    public List<PatientResponseDTO> getAll() {

        return patientRepository.findAll().stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    public PatientResponseDTO createPatient( PatientRequestDTO patient) {
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exists");
        }
        Patient newPatient = PatientMapper.toEntity(patient);
        newPatient.setRegisteredDate(LocalDate.now());

        newPatient =  patientRepository.save(newPatient);

        // call billing service to create a billing account
        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getName(),
                newPatient.getEmail()
        );

        // send event to kafka topic
        kafkaProducer.sendEvent(newPatient);
        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patient) {

        Patient existedPatient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with the id: " + id)
        );

        if (patientRepository.existsByEmailAndIdNot(patient.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email already exists");
        }

        existedPatient.setName(patient.getName());
        existedPatient.setEmail(patient.getEmail());
        existedPatient.setAddress(patient.getAddress());
        existedPatient.setDateOfBirth(LocalDate.parse(patient.getDateOfBirth()));

        Patient upadtedPatient = patientRepository.save(existedPatient);

        return PatientMapper.toDTO(upadtedPatient);
    }

    public void deletePatient(UUID id) {
        Patient existedPatient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with the id: " + id)
        );

        patientRepository.delete(existedPatient);
    }
}
