package com.example.safepass_api.service;

import java.util.List;
import java.util.Optional;

import com.example.safepass_api.domain.dto.request.CreateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.request.CreateStakeHolderWithDocumentsRequestDTO;
import com.example.safepass_api.domain.dto.request.UpdateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.response.StakeHolderResponseDTO;
import com.example.safepass_api.domain.enums.StakeHolderStatus;

public interface StakeHolderService {
    List<StakeHolderResponseDTO> getAllStakeHolders();
    Optional<StakeHolderResponseDTO> getStakeHolderById(String id);
    Optional<StakeHolderResponseDTO> createStakeHolder(CreateStakeHolderRequestDTO createRequest);
    Optional<StakeHolderResponseDTO> createStakeHolderWithDocuments(CreateStakeHolderWithDocumentsRequestDTO createRequest);
    Optional<StakeHolderResponseDTO> updateStakeHolder(String id, UpdateStakeHolderRequestDTO updateRequest);
    
    Optional<StakeHolderResponseDTO> updateStakeHolderByStatus(String id, StakeHolderStatus status);

    boolean deleteStakeHolder(String id);
}
