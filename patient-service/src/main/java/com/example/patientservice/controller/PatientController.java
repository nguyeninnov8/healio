package com.example.patientservice.controller;

import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping()
    public ResponseEntity<List<PatientResponseDTO>>  findAll() {
        return ResponseEntity.ok(patientService.getAll());
    }

    @PostMapping()
    public ResponseEntity<PatientResponseDTO>  createPatient(@Valid @RequestBody PatientRequestDTO patient) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patient);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO, @PathVariable UUID id) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDTO);
    }
}
