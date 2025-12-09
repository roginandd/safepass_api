package com.example.safepass_api.service;

import com.example.safepass_api.domain.entity.EntryLog;

import java.util.*;

import com.example.safepass_api.domain.dto.request.StakeHolderEntryLogRequestDTO;
import com.example.safepass_api.domain.dto.request.VisitorEntryLogRequestDTO;
import com.example.safepass_api.domain.dto.response.StakeHolderEntryLogResponseDTO;


public interface EntryLogService {

    // Stake Holder Entry Logs Methods

    // Get
    List<StakeHolderEntryLogResponseDTO> getAllStakeHolderEntryLogs();
    List<StakeHolderEntryLogResponseDTO> getEntryLogsByStakeHolderId(String stakeHolderId);

    // Post
    Optional<StakeHolderEntryLogResponseDTO> createStakeHolderEntryLog(StakeHolderEntryLogRequestDTO dto);

    // Visitor Entry Logs Methods
    
    // // Get
    // List<StakeHolderEntryLogResponseDTO> getAllVisitorEntryLogs();
    // List<StakeHolderEntryLogResponseDTO> getEntryLogsByVisitorId(String visitorId);


    // // Post
    // Optional<StakeHolderEntryLogResponseDTO> createVisitorEntryLog(VisitorEntryLogRequestDTO dto);
    

}
