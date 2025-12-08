package com.example.safepass_api.mapper;

import org.mapstruct.Mapper;

import com.example.safepass_api.domain.dto.response.SystemUserResponseDTO;
import com.example.safepass_api.domain.entity.SystemUser;


@Mapper(componentModel="spring")
public interface SystemUserMapper {

    
    SystemUserResponseDTO toResponseDTO(SystemUser systemUser);


    SystemUser toEntity(SystemUserResponseDTO systemUserResponseDTO);


}
