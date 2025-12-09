package com.example.safepass_api.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.safepass_api.domain.dto.request.UploadDocumentRequestDTO;
import com.example.safepass_api.domain.dto.response.DocumentResponseDTO;
import com.example.safepass_api.domain.entity.Documents;
import com.example.safepass_api.domain.entity.StakeHolder;
import com.example.safepass_api.exception.BadRequestException;
import com.example.safepass_api.exception.ResourceNotFoundException;
import com.example.safepass_api.mapper.DocumentMapper;
import com.example.safepass_api.repository.DocumentRepository;
import com.example.safepass_api.repository.StakeHolderRepository;
import com.example.safepass_api.service.DocumentService;
import com.example.safepass_api.service.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{

    private final DocumentRepository documentRepository;
    private final StakeHolderRepository stakeHolderRepository;
    private final S3Service s3Service;
    private final DocumentMapper documentMapper;

    @Value("${file.root-url}")
    private String rootUrl;
    /**
     * Upload document for a stake holder
     */
    @Override
    public DocumentResponseDTO uploadDocument(UploadDocumentRequestDTO uploadRequest, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File cannot be empty");
        }

        StakeHolder stakeHolder = stakeHolderRepository.findById(uploadRequest.getStakeHolderId())
                .orElseThrow(() -> new ResourceNotFoundException("StakeHolder not found"));

        // Upload to S3
        String fileName = s3Service.uploadFile(file, uploadRequest.getStakeHolderId());

        
        // Create document record
        Documents document = Documents.builder()
                .documentType(uploadRequest.getDocumentType())
                .documentUrl(fileName)
                .stakeHolder(stakeHolder)
                .build();

        document.setDocumentUrl(rootUrl + document.getDocumentUrl());
        Documents savedDocument = documentRepository.save(document);
        return documentMapper.toResponseDTO(savedDocument);
    }

    /**
     * Get all documents for a stake holder
     */
    @Override
    public List<DocumentResponseDTO> getDocumentsByStakeHolderId(String stakeHolderId) {
        // Verify stake holder exists
        stakeHolderRepository.findById(stakeHolderId)
                .orElseThrow(() -> new ResourceNotFoundException("StakeHolder not found"));

        return documentRepository.findByStakeHolderId(stakeHolderId)
                .stream()
                .map(documentMapper::toResponseDTO)
                .toList();
    }

    /**
     * Get document by ID
     */
    @Override
    public Optional<DocumentResponseDTO> getDocumentById(String documentId) {
        return documentRepository.findById(documentId)
                .map(documentMapper::toResponseDTO);
    }

    /**
     * Delete document
     */
    @Override
    public boolean deleteDocument(String documentId) {
        Documents document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));

        // Delete from S3
        s3Service.deleteFile(document.getDocumentUrl());

        // Delete from database
        documentRepository.deleteById(documentId);
        return true;
    }

    /**
     * Download document
     */
    @Override
    public byte[] downloadDocument(String documentId) throws IOException {
        Documents document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));

        return s3Service.downloadFile(document.getDocumentUrl());
    }
}
