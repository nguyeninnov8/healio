package com.example.patientservice.service;

import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.exception.EmailAlreadyExistsException;
import com.example.patientservice.exception.PatientNotFoundException;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

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

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patient) {

        Patient existedPatient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with the id: " + id)
        );

        existedPatient.setName(patient.getName());
        existedPatient.setEmail(patient.getEmail());
        existedPatient.setAddress(patient.getAddress());
        existedPatient.setDateOfBirth(LocalDate.parse(patient.getDateOfBirth()));

        Patient upadtedPatient = patientRepository.save(existedPatient);

        return PatientMapper.toDTO(upadtedPatient);
    }
}
