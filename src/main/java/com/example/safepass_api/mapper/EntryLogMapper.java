package com.example.safepass_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.safepass_api.domain.dto.request.StakeHolderEntryLogRequestDTO;
import com.example.safepass_api.domain.dto.request.VisitorEntryLogRequestDTO;
import com.example.safepass_api.domain.dto.response.StakeHolderEntryLogResponseDTO;
import com.example.safepass_api.domain.dto.response.VisitorEntryLogResponseDTO;
import com.example.safepass_api.domain.entity.EntryLog;


@Mapper(componentModel = "spring")
public interface EntryLogMapper {

    // From Entity to StakeHolderEntryLogResponseDTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "stakeHolderId", source = "stakeHolder.id")
    @Mapping(target = "firstName", source = "stakeHolder.firstName")
    @Mapping(target = "middleName", source = "stakeHolder.middleName")
    @Mapping(target = "lastName", source = "stakeHolder.lastName")
    @Mapping(target = "entryTime", source = "entryTime")
    StakeHolderEntryLogResponseDTO toStakeHolderEntryLogResponseDTO(EntryLog entryLog);

    // From Entity to VisitorEntryLogResponseDTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "visitorId", source = "visitor.id")
    @Mapping(target = "firstName", source = "visitor.firstName")
    @Mapping(target = "middleName", source = "visitor.middleName")
    @Mapping(target = "lastName", source = "visitor.lastName")
    @Mapping(target = "purposeOfVisit", source = "visitor.purposeOfVisit")
    @Mapping(target = "entryTime", source = "entryTime")
    @Mapping(target = "exitTime", source = "exitTime")
    VisitorEntryLogResponseDTO toVisitorEntryLogResponseDTO(EntryLog entryLog);

    // From StakeHolderEntryLogRequestDTO to EntryLog entity
    @Mapping(target = "entryTime", source = "entryTime")
    @Mapping(target = "stakeHolder.id", source = "stakeHolderId")
    EntryLog toEntity(StakeHolderEntryLogRequestDTO dto);

    // From VisitorEntryLogRequestDTO to EntryLog entity
    @Mapping(target = "visitor.id", source = "visitorId")
    @Mapping(target = "entryTime", source = "entryTime") 
    EntryLog toEntity(VisitorEntryLogRequestDTO dto);    
}
