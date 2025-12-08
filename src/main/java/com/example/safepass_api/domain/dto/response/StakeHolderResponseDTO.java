package com.example.safepass_api.domain.dto.response;

import java.time.LocalDateTime;

import com.example.safepass_api.domain.enums.StakeHolderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StakeHolderResponseDTO {
    private String id;

    private String firstName;
    private String middleName;
    private String lastName;

    private String email;
    private String contactNo;
    private StakeHolderStatus status;

    
}
