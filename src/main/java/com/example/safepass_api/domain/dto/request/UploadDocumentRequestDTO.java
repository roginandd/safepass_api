package com.example.safepass_api.domain.dto.request;

import com.example.safepass_api.domain.enums.DocumentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadDocumentRequestDTO {
    private String stakeHolderId;
    private DocumentType documentType;
}
