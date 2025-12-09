package com.example.safepass_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.safepass_api.domain.dto.request.StakeHolderEntryLogRequestDTO;
import com.example.safepass_api.domain.dto.response.StakeHolderEntryLogResponseDTO;
import com.example.safepass_api.domain.dto.response.StakeHolderResponseDTO;
import com.example.safepass_api.domain.entity.EntryLog;
import com.example.safepass_api.domain.entity.StakeHolder;
import com.example.safepass_api.domain.entity.SystemUser;
import com.example.safepass_api.mapper.EntryLogMapper;
import com.example.safepass_api.mapper.StakeHolderMapper;
import com.example.safepass_api.repository.EntryLogRepository;
import com.example.safepass_api.repository.StakeHolderRepository;
import com.example.safepass_api.repository.SystemUserRepository;
import com.example.safepass_api.service.EntryLogService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class EntryLogServiceImpl implements EntryLogService {

    private final StakeHolderRepository stakeHolderRepository;
    private final EntryLogRepository entryLogRepository;
    private final SystemUserRepository systemUserRepository;
    
    private final EntryLogMapper entryLogMapper;

    @Override
    public List<StakeHolderEntryLogResponseDTO> getAllStakeHolderEntryLogs() {
        List<EntryLog> stakeHolderEntryLogs = entryLogRepository.findAll().stream()
            .filter(arg -> arg.getStakeHolder() != null)
            .toList();

        return stakeHolderEntryLogs.stream().map(entryLogMapper::toStakeHolderEntryLogResponseDTO).toList();
    }
    
    @Override
    public List<StakeHolderEntryLogResponseDTO> getEntryLogsByStakeHolderId(String stakeHolderId) {
        List<StakeHolderEntryLogResponseDTO> stakeHolderEntryLogs = getAllStakeHolderEntryLogs();

        return stakeHolderEntryLogs.stream()
            .filter(arg -> arg.getStakeHolderId().equals(stakeHolderId))
            .toList();
    }

    @Override
    public Optional<StakeHolderEntryLogResponseDTO> createStakeHolderEntryLog(StakeHolderEntryLogRequestDTO dto) {
        String systemUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        dto.setEntryTime(java.time.LocalDateTime.now());
        dto.setSystemUserId(systemUserId);
       
       
        StakeHolder existingStakeHolder = stakeHolderRepository.findById(dto.getStakeHolderId())
            .orElseThrow(() -> new RuntimeException("Stake Holder not found"));

        SystemUser systemUser = systemUserRepository.findById(dto.getSystemUserId())
            .orElseThrow(() -> new RuntimeException("System User not found"));

        System.out.println("Creating entry log for StakeHolder ID: " + dto.getStakeHolderId() + " by SystemUser ID: " + dto.getSystemUserId());
        
        
        EntryLog entryLog = entryLogMapper.toEntity(dto);
        entryLog.setStakeHolder(existingStakeHolder);
        entryLog.setSystemUser(systemUser);

        EntryLog saved = entryLogRepository.save(entryLog);
        StakeHolderEntryLogResponseDTO responseDTO = entryLogMapper.toStakeHolderEntryLogResponseDTO(saved);

        return Optional.ofNullable(responseDTO);
    }

}
