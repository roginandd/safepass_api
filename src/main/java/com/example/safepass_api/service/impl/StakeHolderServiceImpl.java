package com.example.safepass_api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.safepass_api.domain.dto.request.CreateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.request.CreateStakeHolderWithDocumentsRequestDTO;
import com.example.safepass_api.domain.dto.request.UpdateStakeHolderRequestDTO;
import com.example.safepass_api.domain.dto.request.UploadDocumentRequestDTO;
import com.example.safepass_api.domain.dto.response.StakeHolderResponseDTO;
import com.example.safepass_api.domain.entity.Documents;
import com.example.safepass_api.domain.entity.StakeHolder;
import com.example.safepass_api.domain.enums.DocumentType;
import com.example.safepass_api.domain.enums.StakeHolderStatus;
import com.example.safepass_api.exception.BadRequestException;
import com.example.safepass_api.exception.ResourceNotFoundException;
import com.example.safepass_api.mapper.StakeHolderMapper;
import com.example.safepass_api.repository.StakeHolderRepository;
import com.example.safepass_api.service.DocumentService;
import com.example.safepass_api.service.StakeHolderService;
import com.example.safepass_api.domain.dto.request.CreateStakeHolderWithDocumentsRequestDTO;
import com.example.safepass_api.domain.entity.Documents;
import com.example.safepass_api.domain.enums.DocumentType;
import com.example.safepass_api.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StakeHolderServiceImpl implements StakeHolderService {

    private final StakeHolderRepository stakeHolderRepository;
    private final StakeHolderMapper stakeHolderMapper;
    private final DocumentService documentService;

    @Override
    public List<StakeHolderResponseDTO> getAllStakeHolders() {
        return stakeHolderRepository.findAll()
                .stream()
                .map(stakeHolderMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<StakeHolderResponseDTO> getStakeHolderById(String id) {
        return stakeHolderRepository.findById(id)
                .map(stakeHolderMapper::toResponseDTO);
    }

    @Override
    public Optional<StakeHolderResponseDTO> createStakeHolder(CreateStakeHolderRequestDTO createRequest) {
        // Validate required fields
        if (createRequest.getFirstName() == null || createRequest.getFirstName().isEmpty() ||
            createRequest.getLastName() == null || createRequest.getLastName().isEmpty() ||
            createRequest.getEmail() == null || createRequest.getEmail().isEmpty() ||
            createRequest.getContactNo() == null || createRequest.getContactNo().isEmpty()) {
            throw new BadRequestException("First name, last name, email, and contact number are required");
        }


        StakeHolder stakeHolder = stakeHolderMapper.toEntity(createRequest);

        stakeHolder.setStatus(StakeHolderStatus.PENDING);

        StakeHolder savedStakeHolder = stakeHolderRepository.save(stakeHolder);
        return Optional.of(stakeHolderMapper.toResponseDTO(savedStakeHolder));
    }

    @Override
    public Optional<StakeHolderResponseDTO> updateStakeHolder(String id, UpdateStakeHolderRequestDTO updateRequest) {
        StakeHolder stakeHolder = stakeHolderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StakeHolder not found"));

        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().isEmpty()) {
            stakeHolder.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getMiddleName() != null && !updateRequest.getMiddleName().isEmpty()) {
            stakeHolder.setMiddleName(updateRequest.getMiddleName());
        }
        if (updateRequest.getLastName() != null && !updateRequest.getLastName().isEmpty()) {
            stakeHolder.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            stakeHolder.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getContactNo() != null && !updateRequest.getContactNo().isEmpty()) {
            stakeHolder.setContactNo(updateRequest.getContactNo());
        }

        StakeHolder updatedStakeHolder = stakeHolderRepository.save(stakeHolder);
        return Optional.of(stakeHolderMapper.toResponseDTO(updatedStakeHolder));
    }

    @Override
    public boolean deleteStakeHolder(String id) {
        if (!stakeHolderRepository.existsById(id)) {
            throw new ResourceNotFoundException("StakeHolder not found");
        }
        stakeHolderRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public Optional<StakeHolderResponseDTO> createStakeHolderWithDocuments(CreateStakeHolderWithDocumentsRequestDTO createRequest) {
        // Validate required fields
        if (createRequest.getFirstName() == null || createRequest.getFirstName().isEmpty() ||
            createRequest.getLastName() == null || createRequest.getLastName().isEmpty() ||
            createRequest.getEmail() == null || createRequest.getEmail().isEmpty() ||
            createRequest.getContactNo() == null || createRequest.getContactNo().isEmpty()) {
            throw new BadRequestException("First name, last name, email, and contact number are required");
        }

        // Validate exactly 3 documents are provided
        if (createRequest.getDocument1() == null || createRequest.getDocument1().isEmpty() ||
            createRequest.getDocument2() == null || createRequest.getDocument2().isEmpty() ||
            createRequest.getDocument3() == null || createRequest.getDocument3().isEmpty()) {
            throw new BadRequestException("Exactly 3 documents are required: GOV_ID, PHOTO_2X2, and NBI_CLEARANCE");
        }

        // Check for duplicate email or contact
        if (stakeHolderRepository.existsByEmail(createRequest.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        if (stakeHolderRepository.existsByContactNo(createRequest.getContactNo())) {
            throw new BadRequestException("Contact number already exists");
        }

        // Create stakeholder with PENDING status initially
        StakeHolder stakeHolder = StakeHolder.builder()
                .firstName(createRequest.getFirstName())
                .middleName(createRequest.getMiddleName())
                .lastName(createRequest.getLastName())
                .email(createRequest.getEmail())
                .contactNo(createRequest.getContactNo())
                .status(StakeHolderStatus.PENDING)
                .build();

        StakeHolder savedStakeHolder = stakeHolderRepository.save(stakeHolder);
        StakeHolder stakeHolderWithDocuments = null;

        // Upload documents
        try {
            // Document 1: GOV_ID
            UploadDocumentRequestDTO doc1Request = UploadDocumentRequestDTO.builder()
                    .stakeHolderId(savedStakeHolder.getId())
                    .documentType(DocumentType.GOV_ID)
                    .build();
            documentService.uploadDocument(doc1Request, createRequest.getDocument1());

            // Document 2: PHOTO_2X2
            UploadDocumentRequestDTO doc2Request = UploadDocumentRequestDTO.builder()
                    .stakeHolderId(savedStakeHolder.getId())
                    .documentType(DocumentType.PHOTO_2X2)
                    .build();
            documentService.uploadDocument(doc2Request, createRequest.getDocument2());

            // Document 3: NBI_CLEARANCE
            UploadDocumentRequestDTO doc3Request = UploadDocumentRequestDTO.builder()
                    .stakeHolderId(savedStakeHolder.getId())
                    .documentType(DocumentType.NBI_CLEARANCE)
                    .build();
            documentService.uploadDocument(doc3Request, createRequest.getDocument3());

            stakeHolderWithDocuments = stakeHolderRepository.save(savedStakeHolder);

        } catch (Exception e) {
            // If document upload fails, delete the stakeholder and throw error
            stakeHolderRepository.delete(savedStakeHolder);
            throw new BadRequestException("Failed to upload documents: " + e.getMessage());
        }

        // Build response DTO and include uploaded documents (S3 links)
        StakeHolderResponseDTO responseDto = stakeHolderMapper.toResponseDTO(stakeHolderWithDocuments);
        try {
            var docs = documentService.getDocumentsByStakeHolderId(stakeHolderWithDocuments.getId());
            responseDto.setDocuments(docs);
        } catch (Exception ex) {
            // if fetching documents fails, still return stakeholder DTO without documents
        }

        return Optional.of(responseDto);
    }

    @Override
    @Transactional
    public Optional<StakeHolderResponseDTO> updateStakeHolderByStatus(String id, StakeHolderStatus status) {
         StakeHolder stakeHolder = stakeHolderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StakeHolder not found"));

        stakeHolder.setStatus(status);

        StakeHolder updatedStakeHolder = stakeHolderRepository.save(stakeHolder);

        return Optional.of(stakeHolderMapper.toResponseDTO(updatedStakeHolder));

    }

}
