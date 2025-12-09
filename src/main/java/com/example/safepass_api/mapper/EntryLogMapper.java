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
    @Mapping(target = "stakeHolderId", source = "stakeHolder.id")
    @Mapping(target = "firstName", source = "stakeHolder.firstName")
    @Mapping(target = "middleName", source = "stakeHolder.middleName")
    @Mapping(target = "lastName", source = "stakeHolder.lastName")
    @Mapping(target = "entryTime", source = "entryTime")
    StakeHolderEntryLogResponseDTO toStakeHolderEntryLogResponseDTO(EntryLog entryLog);

    // From Entity to VisitorEntryLogResponseDTO
    @Mapping(target = "visitorId", source = "visitorSession.visitor.id")
    @Mapping(target = "firstName", source = "visitorSession.visitor.firstName")
    @Mapping(target = "middleName", source = "visitorSession.visitor.middleName")
    @Mapping(target = "lastName", source = "visitorSession.visitor.lastName")
    @Mapping(target = "purposeOfVisit", source = "visitorSession.purposeOfVisit")
    @Mapping(target = "entryTime", source = "entryTime")
    @Mapping(target = "exitTime", source = "exitTime")
    VisitorEntryLogResponseDTO toVisitorEntryLogResponseDTO(EntryLog entryLog);

    // From StakeHolderEntryLogRequestDTO to EntryLog entity
    @Mapping(target = "entryTime", source = "entryTime")
    @Mapping(target = "stakeHolder.firstName", source = "firstName")
    @Mapping(target = "stakeHolder.middleName", source = "middleName")
    @Mapping(target = "stakeHolder.lastName", source = "lastName")
    @Mapping(target = "stakeHolder.email", source = "email")
    @Mapping(target = "stakeHolder.contactNo", source = "contactNo")
    @Mapping(target = "stakeHolder.id", source = "stakeHolderId")
    EntryLog toEntity(StakeHolderEntryLogRequestDTO dto);

    // From VisitorEntryLogRequestDTO to EntryLog entity
    @Mapping(target = "visitorSession.id", source = "visitorId")
    @Mapping(target = "entryTime", source = "entryTime")
    @Mapping(target = "visitorSession.visitor.firstName", source = "firstName")
    @Mapping(target = "visitorSession.visitor.middleName", source = "middleName")
    @Mapping(target = "visitorSession.visitor.lastName", source = "lastName")
    @Mapping(target = "visitorSession.purposeOfVisit", source = "purposeOfVisit")
    @Mapping(target = "visitorSession.visitor.email", source = "email")
    @Mapping(target = "visitorSession.visitor.contactNo", source = "contactNo")
    EntryLog toEntity(VisitorEntryLogRequestDTO dto);    
}
