package com.example.safepass_api.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.safepass_api.domain.dto.request.CreateSystemUserRequestDTO;
import com.example.safepass_api.domain.dto.response.SystemUserResponseDTO;
import com.example.safepass_api.domain.entity.SystemUser;
import com.example.safepass_api.mapper.SystemUserMapper;
import com.example.safepass_api.repository.SystemUserRepository;
import com.example.safepass_api.service.SystemUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemUserServiceImpl implements  SystemUserService{
    
    private final SystemUserRepository systemUserRepository;
    private final SystemUserMapper systemUserMapper;


    @Override
    public List<SystemUserResponseDTO> getAllSystemUsers() {
        
        return systemUserRepository.findAll()
                .stream()
                .map(systemUserMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<SystemUserResponseDTO> getSystemUserById(String id) {
        SystemUser existingSystemUser = systemUserRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("System User not found");
        });

        SystemUserResponseDTO responseDTO = systemUserMapper.toResponseDTO(existingSystemUser);

        return Optional.of(responseDTO);

    }

    
    @Override
    public Optional<SystemUserResponseDTO> getSystemUserByUsername(String username) {
        SystemUser existingSystemUser = systemUserRepository.findByUsername(username).orElseThrow(() -> {
            throw new RuntimeException("System User not found");
        });

        SystemUserResponseDTO responseDTO = systemUserMapper.toResponseDTO(existingSystemUser);

        return Optional.of(responseDTO);
    }

    @Override
    public Optional<SystemUserResponseDTO> createSystemUser(CreateSystemUserRequestDTO createSystemUserRequestDTO) {
        SystemUser systemUserEntity = systemUserMapper.toEntity(createSystemUserRequestDTO);

        SystemUser savedSystemUser = systemUserRepository.save(systemUserEntity);
        SystemUserResponseDTO responseDTO = systemUserMapper.toResponseDTO(savedSystemUser);

        return Optional.of(responseDTO);
    }

}
