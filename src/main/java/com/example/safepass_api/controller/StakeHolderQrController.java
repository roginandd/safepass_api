package com.example.safepass_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.safepass_api.domain.dto.request.CreateQrRequestDTO;
import com.example.safepass_api.domain.dto.response.QrResponseDTO;
import com.example.safepass_api.exception.ResourceNotFoundException;
import com.example.safepass_api.service.StakeHolderQrCodeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stake-holder-qr")
@RequiredArgsConstructor
public class StakeHolderQrController {

    private final StakeHolderQrCodeService stakeHolderQrCodeService;

    @GetMapping("/all")
    public ResponseEntity<List<QrResponseDTO>> getAllQrCodes() {
        List<QrResponseDTO> qrCodes = stakeHolderQrCodeService.getAllQrCodes();
        return ResponseEntity.ok(qrCodes);
    }

    @GetMapping("/stakeholder/{id}")
    public ResponseEntity<QrResponseDTO> getQrByStakeHolderId(@PathVariable String id) {
        QrResponseDTO qr = stakeHolderQrCodeService.getQrCodeByStakeHolderId(id)
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found for StakeHolder id: " + id));
        return ResponseEntity.ok(qr);
    }

    @PostMapping("/create")
    public ResponseEntity<QrResponseDTO> createQr(@RequestBody CreateQrRequestDTO request) {
        QrResponseDTO created = stakeHolderQrCodeService.createQrCodeForStakeHolder(request)
                .orElseThrow(() -> new RuntimeException("Failed to create QR Code"));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}
