package com.example.safepass_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.safepass_api.domain.dto.request.CreateSystemUserRequestDTO;
import com.example.safepass_api.domain.dto.response.SystemUserResponseDTO;
import com.example.safepass_api.exception.BadRequestException;
import com.example.safepass_api.exception.ResourceNotFoundException;
import com.example.safepass_api.service.SystemUserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/system-users")
@RequiredArgsConstructor
public class SystemUserController {
    private final SystemUserService systemUserService;

    @GetMapping("/all")
    public ResponseEntity<List<SystemUserResponseDTO>> getAllSystemUsers() {
        List<SystemUserResponseDTO> systemUsers = systemUserService.getAllSystemUsers();
        
    
        
        return ResponseEntity.ok(systemUsers);
    }

    @PostMapping("/create")
    public ResponseEntity<SystemUserResponseDTO> createSystemUser(@RequestBody CreateSystemUserRequestDTO createSystemUserRequestDTO) {
        SystemUserResponseDTO createdSystemUser = systemUserService
            .createSystemUser(createSystemUserRequestDTO)
            .orElseThrow(() -> new BadRequestException("Failed to create System User"));

        return ResponseEntity.ok(createdSystemUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemUserResponseDTO> getSystemUserById(@PathVariable String id) {
        SystemUserResponseDTO systemUser = systemUserService
            .getSystemUserById(id)
            .orElseThrow(() -> new ResourceNotFoundException("System User not found"));

        return ResponseEntity.ok(systemUser);
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<SystemUserResponseDTO> getSystemUserByUsername(@PathVariable String username) {
        SystemUserResponseDTO systemUser = systemUserService
            .getSystemUserByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("System User not found"));

        return ResponseEntity.ok(systemUser);
    }
    
    
}
