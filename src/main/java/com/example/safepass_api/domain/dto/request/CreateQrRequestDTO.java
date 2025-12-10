package com.example.safepass_api.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateQrRequestDTO {

    private String stakeHolderID;
    
    private String systemUserId;
}
