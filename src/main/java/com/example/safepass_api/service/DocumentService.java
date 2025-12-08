package com.example.safepass_api.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.safepass_api.domain.dto.request.UploadDocumentRequestDTO;
import com.example.safepass_api.domain.dto.response.DocumentResponseDTO;

public interface DocumentService {
    DocumentResponseDTO uploadDocument(UploadDocumentRequestDTO uploadRequest, MultipartFile file) throws IOException;
    List<DocumentResponseDTO> getDocumentsByStakeHolderId(String stakeHolderId);
    Optional<DocumentResponseDTO> getDocumentById(String documentId);
     boolean deleteDocument(String documentId);
     byte[] downloadDocument(String documentId) throws IOException;

}
