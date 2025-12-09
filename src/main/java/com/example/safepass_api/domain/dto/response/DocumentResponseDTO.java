package com.example.safepass_api.domain.dto.response;

import java.time.LocalDateTime;

import com.example.safepass_api.domain.enums.DocumentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDTO {
    private String id;
    private String stakeHolderId;
    private String fileName;
    private DocumentType documentType;
}
