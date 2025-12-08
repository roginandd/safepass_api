package com.example.safepass_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.safepass_api.domain.dto.request.CreateSystemUserRequestDTO;
import com.example.safepass_api.domain.dto.response.SystemUserResponseDTO;
import com.example.safepass_api.domain.entity.SystemUser;


@Mapper(componentModel="spring")
public interface SystemUserMapper {

    @Mapping(target="contactNumber", source="contactNo")
    SystemUserResponseDTO toResponseDTO(SystemUser systemUser);


    @Mapping(target="contactNo", source="contactNumber")
    SystemUser toEntity(CreateSystemUserRequestDTO createSystemUserRequestDTO);


}
