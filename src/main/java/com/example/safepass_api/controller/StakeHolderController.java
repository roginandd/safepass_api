package com.example.safepass_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.safepass_api.domain.dto.request.CreateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.request.UpdateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.response.StakeHolderResponseDTO;
import com.example.safepass_api.exception.ResourceNotFoundException;
import com.example.safepass_api.service.StakeHolderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stake-holders")
@RequiredArgsConstructor
public class StakeHolderController {

    private final StakeHolderService stakeHolderService;

    /**
     * Get all stake holders
     */
    @GetMapping("/all")
    public ResponseEntity<List<StakeHolderResponseDTO>> getAllStakeHolders() {
        List<StakeHolderResponseDTO> stakeHolders = stakeHolderService.getAllStakeHolders();
        return ResponseEntity.ok(stakeHolders);
    }

    /**
     * Get stake holder by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<StakeHolderResponseDTO> getStakeHolderById(@PathVariable String id) {
        StakeHolderResponseDTO stakeHolder = stakeHolderService.getStakeHolderById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StakeHolder not found"));
        return ResponseEntity.ok(stakeHolder);
    }

    /**
     * Create a new stake holder
     */
    @PostMapping("/create")
    public ResponseEntity<StakeHolderResponseDTO> createStakeHolder(@RequestBody CreateStakeHolderRequestDTO createRequest) {
        StakeHolderResponseDTO createdStakeHolder = stakeHolderService.createStakeHolder(createRequest)
                .orElseThrow(() -> new RuntimeException("Failed to create StakeHolder"));
                
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStakeHolder);
    }

    /**
     * Update an existing stake holder
     */
    @PutMapping("/{id}")
    public ResponseEntity<StakeHolderResponseDTO> updateStakeHolder(@PathVariable String id, @RequestBody UpdateStakeHolderRequestDTO updateRequest) {
        StakeHolderResponseDTO updatedStakeHolder = stakeHolderService.updateStakeHolder(id, updateRequest)
                .orElseThrow(() -> new ResourceNotFoundException("StakeHolder not found"));
        return ResponseEntity.ok(updatedStakeHolder);
    }

    /**
     * Delete a stake holder
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStakeHolder(@PathVariable String id) {
        stakeHolderService.deleteStakeHolder(id);
        return ResponseEntity.noContent().build();
    }
}
