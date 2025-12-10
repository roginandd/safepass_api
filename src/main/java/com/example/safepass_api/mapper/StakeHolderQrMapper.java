package com.example.safepass_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.safepass_api.domain.dto.request.CreateQrRequestDTO;
import com.example.safepass_api.domain.dto.response.QrResponseDTO;
import com.example.safepass_api.domain.entity.StakeHolderQrCode;

@Mapper(componentModel="spring", uses={SystemUserMapper.class, StakeHolderMapper.class})
public interface StakeHolderQrMapper {


    QrResponseDTO toResponseDTO (StakeHolderQrCode qr);

   @Mapping(target="stakeHolder.id", source="stakeHolderID")
    @Mapping(target="issuer.id", source="systemUserId")
    StakeHolderQrCode toEntity (CreateQrRequestDTO qr);
}
