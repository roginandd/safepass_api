package com.example.safepass_api.service;

import java.util.List;
import java.util.Optional;

import com.example.safepass_api.domain.dto.request.CreateSystemUserRequestDTO;
import com.example.safepass_api.domain.dto.response.SystemUserResponseDTO;
public interface SystemUserService {

    List<SystemUserResponseDTO> getAllSystemUsers();
    Optional<SystemUserResponseDTO> getSystemUserById(String id);
    Optional<SystemUserResponseDTO> getSystemUserByUsername(String username);
    Optional<SystemUserResponseDTO> createSystemUser(CreateSystemUserRequestDTO createSystemUserRequestDTO);
}
