package com.example.safepass_api.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitorEntryLogResponseDTO {
    private String visitorId;
    private String firstName;
    private String middleName;
    private String lastName;

    private String purposeOfVisit;
    
    private String entryTime;
    private String exitTime;
}
