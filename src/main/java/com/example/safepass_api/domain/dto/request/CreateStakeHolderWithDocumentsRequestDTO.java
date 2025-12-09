package com.example.safepass_api.domain.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStakeHolderWithDocumentsRequestDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNo;

    // Required 3 documents
    private MultipartFile document1; // e.g., GOV_ID
    private MultipartFile document2; // e.g., PHOTO_2X2
    private MultipartFile document3; // e.g., NBI_CLEARANCE
}
