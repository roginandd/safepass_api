package com.example.safepass_api.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStakeHolderRequestDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNo;
}
