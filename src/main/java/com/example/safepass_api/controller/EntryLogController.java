package com.example.safepass_api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.safepass_api.domain.dto.request.StakeHolderEntryLogRequestDTO;
import com.example.safepass_api.domain.dto.response.StakeHolderEntryLogResponseDTO;
import com.example.safepass_api.service.EntryLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/entry-logs")
@RequiredArgsConstructor
public class EntryLogController {

    private final EntryLogService entryLogService;

    @GetMapping("/stakeholders")
    public ResponseEntity<List<StakeHolderEntryLogResponseDTO>> getAllStakeHolderEntryLogs() {
        List<StakeHolderEntryLogResponseDTO> logs = entryLogService.getAllStakeHolderEntryLogs();
          
        if (logs.isEmpty() || logs == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logs);
        }
        
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/stakeholders/{stakeHolderId}")
    public ResponseEntity<List<StakeHolderEntryLogResponseDTO>> getEntryLogsByStakeHolderId(@PathVariable String stakeHolderId) {
        List<StakeHolderEntryLogResponseDTO> logs = entryLogService.getEntryLogsByStakeHolderId(stakeHolderId);
        
        if (logs.isEmpty() || logs == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logs);
        }
        
        return ResponseEntity.ok(logs);
    }

    @PostMapping("/stakeholders")
    public ResponseEntity<StakeHolderEntryLogResponseDTO> createStakeHolderEntryLog(@RequestBody StakeHolderEntryLogRequestDTO request) {
    
        Optional<StakeHolderEntryLogResponseDTO> created = entryLogService.createStakeHolderEntryLog(request);
        return created.map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
