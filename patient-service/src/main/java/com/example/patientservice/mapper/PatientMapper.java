package com.example.patientservice.mapper;

import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient p){
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(p.getId().toString());
        dto.setEmail(p.getEmail());
        dto.setAddress(p.getAddress());
        dto.setName(p.getName());
        dto.setDateOfBirth(p.getDateOfBirth().toString());
        dto.setRegisteredDate(p.getRegisteredDate().toString());
        return dto;
    }

    public static Patient toEntity(PatientRequestDTO dto){
        Patient p = new Patient();
        p.setEmail(dto.getEmail());
        p.setAddress(dto.getAddress());
        p.setName(dto.getName());
        p.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        return p;
    }
}
