package com.example.safepass_api.domain.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrResponseDTO {
    private StakeHolderResponseDTO stakeHolder;

    private SystemUserResponseDTO systemUser;
    private String code;

    private LocalDateTime expiresAt;
}
