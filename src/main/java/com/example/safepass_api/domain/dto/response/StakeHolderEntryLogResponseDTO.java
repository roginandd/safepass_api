package com.example.safepass_api.domain.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StakeHolderEntryLogResponseDTO {
    private String id;

    private String stakeHolderId;
    
    private String firstName;
    private String middleName;
    private String lastName;

    private LocalDateTime entryTime;

}
