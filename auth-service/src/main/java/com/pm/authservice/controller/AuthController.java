package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.dto.RegisterRequestDTO;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication and Authorization")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Login", description = "Generates a JWT token for the user")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        Optional<LoginResponseDTO> loginResponse = authService.authenticate(loginRequestDTO);
        return loginResponse
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @Operation(summary = "Register a new user", description = "Registers a new user in the system")
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequestDTO registerRequestDTO
            ) {
        return authService.register(registerRequestDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(400).body("Registration failed"));
    }


}
