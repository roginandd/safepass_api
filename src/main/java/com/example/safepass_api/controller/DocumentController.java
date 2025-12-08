package com.example.safepass_api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.safepass_api.domain.dto.request.UploadDocumentRequestDTO;
import com.example.safepass_api.domain.dto.response.DocumentResponseDTO;
import com.example.safepass_api.domain.enums.DocumentType;
import com.example.safepass_api.service.impl.DocumentServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServiceImpl documentService;

    /**
     * Upload a document for a stake holder
     */
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponseDTO> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("stakeHolderId") String stakeHolderId,
            @RequestParam("documentType") DocumentType documentType) throws IOException {
        
                

        UploadDocumentRequestDTO uploadRequest = UploadDocumentRequestDTO.builder().stakeHolderId(stakeHolderId)
                .documentType(documentType)
                .build();

        DocumentResponseDTO response = documentService.uploadDocument(uploadRequest, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all documents for a stake holder
     */
    @GetMapping("/stakeholder/{stakeHolderId}")
    public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByStakeHolder(
            @PathVariable String stakeHolderId) {
        List<DocumentResponseDTO> documents = documentService.getDocumentsByStakeHolderId(stakeHolderId);
        return ResponseEntity.ok(documents);
    }

    /**
     * Get document by ID
     */
    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentResponseDTO> getDocumentById(
            @PathVariable String documentId) {
        return documentService.getDocumentById(documentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Download document
     */
    @GetMapping("/{documentId}/download")
    public ResponseEntity<byte[]> downloadDocument(
            @PathVariable String documentId) throws IOException {
        byte[] fileContent = documentService.downloadDocument(documentId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"document\"")
                .body(fileContent);
    }

    /**
     * Delete document
     */
    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable String documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}
