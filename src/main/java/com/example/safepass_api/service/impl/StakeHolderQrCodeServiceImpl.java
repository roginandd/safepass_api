package com.example.safepass_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.safepass_api.domain.dto.request.CreateQrRequestDTO;
import com.example.safepass_api.domain.dto.request.CreateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.response.QrResponseDTO;
import com.example.safepass_api.domain.entity.StakeHolder;
import com.example.safepass_api.domain.entity.StakeHolderQrCode;
import com.example.safepass_api.domain.entity.SystemUser;
import com.example.safepass_api.domain.enums.QrCodeStatus;
import com.example.safepass_api.domain.enums.StakeHolderStatus;
import com.example.safepass_api.mapper.StakeHolderQrMapper;
import com.example.safepass_api.repository.StakeHolderQrCodeRepository;
import com.example.safepass_api.repository.StakeHolderRepository;
import com.example.safepass_api.repository.SystemUserRepository;
import com.example.safepass_api.service.StakeHolderQrCodeService;
import com.example.safepass_api.service.StakeHolderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StakeHolderQrCodeServiceImpl implements StakeHolderQrCodeService{

    private final StakeHolderQrCodeRepository stakeHolderQrCodeRepository;
    private final SystemUserRepository systemUserRepository;
    private final StakeHolderRepository stakeHolderRepository;
    
    private final StakeHolderQrMapper stakeHolderQrMapper;


    @Override
    public List<QrResponseDTO> getAllQrCodes() {
        List<QrResponseDTO> qrCodeResponses = stakeHolderQrCodeRepository.findAll()
            .stream()
            .map(stakeHolderQrMapper::toResponseDTO)
            .toList();


        return qrCodeResponses;
    }

    @Override
    public Optional<QrResponseDTO>  getQrCodeByStakeHolderId(String stakeHolderId) {
        QrResponseDTO qrCodeResponse = stakeHolderQrMapper.toResponseDTO(
            stakeHolderQrCodeRepository.findByStakeHolderId(stakeHolderId)
            .orElseThrow(() -> new RuntimeException("QR Code not found for StakeHolder ID: " + stakeHolderId))
        );
        
        return Optional.of(qrCodeResponse);
    } 
    
    @Override
    public Optional<QrResponseDTO> createQrCodeForStakeHolder(CreateQrRequestDTO request) {
        String stakeHolderId = request.getStakeHolderID();
        String systemUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        request.setStakeHolderID(systemUserId);

        StakeHolder stakeHolder = stakeHolderRepository.findById(stakeHolderId)
            .orElseThrow(() -> new RuntimeException("StakeHolder not found with ID: " + stakeHolderId));


        if (stakeHolder.getStatus() != StakeHolderStatus.APPROVED)
            throw new RuntimeException("Cannot create QR Code for StakeHolder with status: " + stakeHolder.getStatus());

        if (stakeHolderQrCodeRepository.existsByStakeHolderId(stakeHolderId))
            throw new RuntimeException("QR Code already exists for StakeHolder ID: " + stakeHolderId);

        SystemUser systemUser = systemUserRepository.findById(systemUserId)
            .orElseThrow(() -> new RuntimeException("System User not found: " + systemUserId));

        StakeHolderQrCode qrCode = stakeHolderQrMapper.toEntity(request);
        
        qrCode.setStakeHolder(stakeHolder);
        qrCode.setIssuer(systemUser);

        
        qrCode.setStatus(QrCodeStatus.ACTIVE);


        StakeHolderQrCode savedQrCode = stakeHolderQrCodeRepository.save(qrCode);


        return Optional.of(stakeHolderQrMapper.toResponseDTO(savedQrCode));

    }

}
