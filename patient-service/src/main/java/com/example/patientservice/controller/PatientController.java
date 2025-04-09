package com.example.patientservice.controller;

import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "Patient Service API")
public class PatientController {

    private final PatientService patientService;

    @GetMapping()
    @Operation(summary = "Get all patients")
    public ResponseEntity<List<PatientResponseDTO>>  findAll() {
        return ResponseEntity.ok(patientService.getAll());
    }

    @PostMapping()
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponseDTO>  createPatient(@Valid @RequestBody PatientRequestDTO patient) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patient);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO, @PathVariable UUID id) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
