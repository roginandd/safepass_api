package com.example.safepass_api.domain.dto.request;

import lombok.Data;

@Data
public class CreateSystemUserRequestDTO {
    private String username;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNumber;

}
