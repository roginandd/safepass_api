package com.example.safepass_api.domain.dto.response;

import lombok.Data;

@Data
public class AuthResponse {
    String token;
    StakeHolderResponseDTO stakeHolderResponseDTO;
}
