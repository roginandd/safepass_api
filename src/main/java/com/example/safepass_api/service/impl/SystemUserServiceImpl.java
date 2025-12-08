package com.example.safepass_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.safepass_api.domain.dto.request.CreateSystemUserRequestDTO;
import com.example.safepass_api.domain.dto.response.SystemUserResponseDTO;
import com.example.safepass_api.domain.entity.SystemUser;
import com.example.safepass_api.mapper.SystemUserMapper;
import com.example.safepass_api.repository.SystemUserRepository;
import com.example.safepass_api.service.SystemUserService;
import com.example.safepass_api.domain.enums.SystemUserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
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
        SystemUser existingSystemUser = systemUserRepository.findByUsername(createSystemUserRequestDTO.getUsername()).orElse(null);

        String username = createSystemUserRequestDTO.getUsername();
        String password = createSystemUserRequestDTO.getPassword();
        String email = createSystemUserRequestDTO.getEmail();
        String firstName = createSystemUserRequestDTO.getFirstName();
        String lastName = createSystemUserRequestDTO.getLastName();
        String contactNumber = createSystemUserRequestDTO.getContactNumber();

                System.out.println("Creating System User: " + createSystemUserRequestDTO.toString());

;
        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty() ||
            email == null || email.isEmpty() ||
            firstName == null || firstName.isEmpty() ||
            lastName == null || lastName.isEmpty() ||
            contactNumber == null || contactNumber.isEmpty()) {
            throw new RuntimeException("Empty Fields");
        }


        if (existingSystemUser != null) {
            return Optional.empty();
        }

        SystemUser systemUserEntity = systemUserMapper.toEntity(createSystemUserRequestDTO);

        systemUserEntity.setRole(SystemUserRole.VERIFIER);

        SystemUser savedSystemUser = systemUserRepository.save(systemUserEntity);
        SystemUserResponseDTO responseDTO = systemUserMapper.toResponseDTO(savedSystemUser);

        return Optional.of(responseDTO);
    }

}
