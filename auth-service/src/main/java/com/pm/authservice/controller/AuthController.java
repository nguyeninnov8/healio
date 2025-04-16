package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
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

    @Operation(summary = "Generate JWT Token", description = "Generates a JWT token for the user")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);
        return tokenOptional.map(s -> ResponseEntity.ok(new LoginResponseDTO(s))).orElseGet(() -> ResponseEntity.status(401).body(null));
    }
}
