package com.example.safepass_api.domain.dto.response;

import com.example.safepass_api.domain.enums.StakeHolderStatus;

import lombok.Data;

@Data
public class StakeHolderResponseDTO {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private StakeHolderStatus status;
}
