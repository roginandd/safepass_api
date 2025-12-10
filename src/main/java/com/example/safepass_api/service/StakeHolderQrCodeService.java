package com.example.safepass_api.service;

import java.util.*;

import com.example.safepass_api.domain.dto.request.CreateQrRequestDTO;
import com.example.safepass_api.domain.dto.request.CreateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.response.QrResponseDTO;

public interface StakeHolderQrCodeService {
    List<QrResponseDTO> getAllQrCodes();

    Optional<QrResponseDTO> getQrCodeByStakeHolderId(String stakeHolderId);
    Optional<QrResponseDTO> createQrCodeForStakeHolder(CreateQrRequestDTO request);
}
