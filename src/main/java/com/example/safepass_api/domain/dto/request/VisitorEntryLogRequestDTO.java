package com.example.safepass_api.domain.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitorEntryLogRequestDTO {
    private String visitorId;

    private String purposeOfVisit;

    private LocalDateTime entryTime;
}
