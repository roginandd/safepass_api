package com.example.safepass_api.domain.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitorEntryLogRequestDTO {
    private String visitorId;


    // basic credentials
    private String firstName;
    private String middleName;
    private String lastName;

    // contact details
    private String email;
    private String contactNo;
    private String purposeOfVisit;

    private LocalDateTime entryTime;
}
