package com.example.safepass_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.safepass_api.domain.dto.request.CreateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.response.StakeHolderResponseDTO;
import com.example.safepass_api.domain.entity.StakeHolder;

@Mapper(componentModel = "spring")
public interface StakeHolderMapper {
    
    StakeHolderResponseDTO toResponseDTO(StakeHolder stakeHolder);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "documents", ignore = true)
    @Mapping(target = "approvals", ignore = true)
    @Mapping(target = "qrCodes", ignore = true)
    @Mapping(target = "entryLogs", ignore = true)
    StakeHolder toEntity(CreateStakeHolderRequestDTO createRequest);
}
