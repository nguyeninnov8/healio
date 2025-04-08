package com.example.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientResponseDTO {
    private String id;
    private String name;
    private String address;
    private String email;
    private String dateOfBirth;
    private String registeredDate;
}
