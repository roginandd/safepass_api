package com.example.safepass_api.domain.dto.response;

import com.example.safepass_api.domain.enums.SystemUserRole;

import lombok.Data;

@Data
public class SystemUserResponseDTO {
    private String id;

    private String firstName;
    private String middleName;
    private String lastName;

    private String email;
    private String username;
    private String contactNumber;
    private SystemUserRole role;
}
