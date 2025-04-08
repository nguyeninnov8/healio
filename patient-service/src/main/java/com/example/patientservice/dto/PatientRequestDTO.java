package com.example.patientservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email()
    @NotBlank
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String dateOfBirth;
}
