package com.example.safepass_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.safepass_api.domain.dto.request.AuthRequest;
import com.example.safepass_api.domain.dto.request.CreateSystemUserRequestDTO;
import com.example.safepass_api.domain.dto.response.AuthResponseDTO;
import com.example.safepass_api.domain.dto.response.SystemUserResponseDTO;
import com.example.safepass_api.exception.BadRequestException;
import com.example.safepass_api.exception.ResourceNotFoundException;
import com.example.safepass_api.service.SystemUserService;
import com.example.safepass_api.service.impl.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SystemUserService systemUserService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register a new system user
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody CreateSystemUserRequestDTO registerRequest) {
        SystemUserResponseDTO createdUser = systemUserService
            .createSystemUser(registerRequest)
            .orElseThrow(() -> new BadRequestException("Failed to register user"));

        String token = jwtService.generateToken(createdUser.getId());
        AuthResponseDTO authResponse = new AuthResponseDTO(token, createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    /**
     * Login with username and password
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new BadRequestException("Username and password are required");
        }

        SystemUserResponseDTO systemUser = systemUserService
            .getSystemUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("System User not found"));

        String token = jwtService.generateToken(systemUser.getId());
        AuthResponseDTO authResponse = new AuthResponseDTO(token, systemUser);

        return ResponseEntity.ok(authResponse);
    }

    /**
     * Get current user profile based on JWT token
     */
    @GetMapping("/profile")
    public ResponseEntity<SystemUserResponseDTO> getProfile() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        
        SystemUserResponseDTO systemUser = systemUserService
            .getSystemUserById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("System User not found"));

        return ResponseEntity.ok(systemUser);
    }
}
