package com.example.safepass_api.domain.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StakeHolderEntryLogResponseDTO {
    private String stakeHolderId;
    
    private String firstName;
    private String middleName;
    private String lastName;

    private LocalDateTime entryTime;

}
