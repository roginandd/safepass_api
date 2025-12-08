package com.example.safepass_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.safepass_api.domain.dto.request.CreateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.request.UpdateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.response.StakeHolderResponseDTO;
import com.example.safepass_api.domain.entity.StakeHolder;
import com.example.safepass_api.domain.enums.StakeHolderStatus;
import com.example.safepass_api.exception.BadRequestException;
import com.example.safepass_api.exception.ResourceNotFoundException;
import com.example.safepass_api.mapper.StakeHolderMapper;
import com.example.safepass_api.repository.StakeHolderRepository;
import com.example.safepass_api.service.StakeHolderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StakeHolderServiceImpl implements StakeHolderService {

    private final StakeHolderRepository stakeHolderRepository;
    private final StakeHolderMapper stakeHolderMapper;

    @Override
    public List<StakeHolderResponseDTO> getAllStakeHolders() {
        return stakeHolderRepository.findAll()
                .stream()
                .map(stakeHolderMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<StakeHolderResponseDTO> getStakeHolderById(String id) {
        return stakeHolderRepository.findById(id)
                .map(stakeHolderMapper::toResponseDTO);
    }

    @Override
    public Optional<StakeHolderResponseDTO> createStakeHolder(CreateStakeHolderRequestDTO createRequest) {
        // Validate required fields
        if (createRequest.getFirstName() == null || createRequest.getFirstName().isEmpty() ||
            createRequest.getLastName() == null || createRequest.getLastName().isEmpty() ||
            createRequest.getEmail() == null || createRequest.getEmail().isEmpty() ||
            createRequest.getContactNo() == null || createRequest.getContactNo().isEmpty()) {
            throw new BadRequestException("First name, last name, email, and contact number are required");
        }


        StakeHolder stakeHolder = stakeHolderMapper.toEntity(createRequest);

        stakeHolder.setStatus(StakeHolderStatus.PENDING);

        StakeHolder savedStakeHolder = stakeHolderRepository.save(stakeHolder);
        return Optional.of(stakeHolderMapper.toResponseDTO(savedStakeHolder));
    }

    @Override
    public Optional<StakeHolderResponseDTO> updateStakeHolder(String id, UpdateStakeHolderRequestDTO updateRequest) {
        StakeHolder stakeHolder = stakeHolderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StakeHolder not found"));

        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().isEmpty()) {
            stakeHolder.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getMiddleName() != null && !updateRequest.getMiddleName().isEmpty()) {
            stakeHolder.setMiddleName(updateRequest.getMiddleName());
        }
        if (updateRequest.getLastName() != null && !updateRequest.getLastName().isEmpty()) {
            stakeHolder.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            stakeHolder.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getContactNo() != null && !updateRequest.getContactNo().isEmpty()) {
            stakeHolder.setContactNo(updateRequest.getContactNo());
        }

        StakeHolder updatedStakeHolder = stakeHolderRepository.save(stakeHolder);
        return Optional.of(stakeHolderMapper.toResponseDTO(updatedStakeHolder));
    }

    @Override
    public boolean deleteStakeHolder(String id) {
        if (!stakeHolderRepository.existsById(id)) {
            throw new ResourceNotFoundException("StakeHolder not found");
        }
        stakeHolderRepository.deleteById(id);
        return true;
    }

}
