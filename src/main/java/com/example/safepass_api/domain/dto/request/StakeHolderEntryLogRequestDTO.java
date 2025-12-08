package com.example.safepass_api.domain.dto.request;


import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StakeHolderEntryLogRequestDTO {
    // basic credentials
    private String firstName;
    private String middleName;
    private String lastName;

    // contact details
    private String email;
    private String contactNo;

    // entry time
    private LocalDateTime entryTime;
}
