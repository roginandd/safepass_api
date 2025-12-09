package com.example.safepass_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.safepass_api.domain.dto.request.UploadDocumentRequestDTO;
import com.example.safepass_api.domain.dto.response.DocumentResponseDTO;
import com.example.safepass_api.domain.entity.Documents;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    
    @Mapping(source = "stakeHolder.id", target = "stakeHolderId")
    @Mapping(source = "documentUrl", target = "fileName")
    DocumentResponseDTO toResponseDTO(Documents document);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "documentUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "stakeHolder", ignore = true)
    Documents toEntity(UploadDocumentRequestDTO uploadRequest);
}
