package com.example.safepass_api.domain.dto.request;


import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StakeHolderEntryLogRequestDTO {
    private String stakeHolderId;
    private String systemUserId;
    private LocalDateTime entryTime;
}
